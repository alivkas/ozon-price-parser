package com.example.ozonpriceparser.api.service.impl;

import com.example.ozonpriceparser.api.PriceSerial;
import com.example.ozonpriceparser.api.dto.PageDto;
import com.example.ozonpriceparser.api.dto.PageResponse;
import com.example.ozonpriceparser.api.events.PriceEvent;
import com.example.ozonpriceparser.api.listeners.PriceEventListener;
import com.example.ozonpriceparser.config.property.ElementsProperties;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import com.example.ozonpriceparser.api.service.PriceService;
import com.microsoft.playwright.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    ElementsProperties elementsProperties;
    ApplicationEventPublisher applicationEventPublisher;
    PriceEventListener priceEventListener;

    @Override
    public PageDto getPrice(String url) throws ElementNotFoundException {
        Playwright playwright = Playwright.create();
        Browser browser = null;
        try {
            browser = playwright.chromium().launch();
            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setUserAgent(elementsProperties.userAgent()));

            Page page = browserContext.newPage();
            page.navigate(url);
            page.waitForTimeout(2000);

            ElementHandle handlePrice = page.querySelector(elementsProperties.elementPrice());
            ElementHandle handleTitle = page.querySelector(elementsProperties.elementTitle());

            if (handlePrice != null && handleTitle != null) {
                String price = handlePrice.innerText();
                applicationEventPublisher.publishEvent(new PriceEvent(url, handleTitle.innerText(), priceToInt(price)));
                return new PageDto(url, handleTitle.innerText(), priceToInt(price));
            } else {
                throw new ElementNotFoundException(elementsProperties.elementPrice(),
                        elementsProperties.elementTitle());
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException(elementsProperties.elementPrice(),
                    elementsProperties.elementTitle());
        } finally {
            if (browser != null) {
                browser.close();
            }
            playwright.close();
        }
    }

    private Integer priceToInt(String price) {
        String normalPrice = price
                .replace("₽", "")
                .replace(" ", "");
        return Integer.parseInt(normalPrice);
    }

    @Override
    public void serializePrice() throws IOException, ElementNotFoundException {
        PageDto pageInfo = getPrice(priceEventListener.getUrl());
        PriceSerial priceSerial = new PriceSerial(pageInfo.price(), pageInfo.title());
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/price/serialize price.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(priceSerial);
        objectOutputStream.close();
    }

    @Override
    public PriceSerial deserializePrice() throws IOException, ClassNotFoundException, ElementNotFoundException {
        File file = new File("src/main/resources/price/serialize price.txt");
        if (file.length() == 0) {
            serializePrice();
        }

        FileInputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        PriceSerial priceSerial = (PriceSerial) objectInputStream.readObject();
        objectInputStream.close();
        return priceSerial;
    }

    @Override
    public PageResponse getDifferencePrice(String url) throws IOException, ClassNotFoundException, ElementNotFoundException {
        Integer currentPrice = getPrice(url).price();
        String currentTitle = priceEventListener.getTitle();
        Integer oldPrice = deserializePrice().getPrice();
        String oldTitle = deserializePrice().getTitle();

        int diffPrice;

        if (!oldTitle.equals(currentTitle)) {
            serializePrice();
            oldPrice = getPrice(url).price();
            oldTitle = currentTitle;
        }
        diffPrice = currentPrice - oldPrice;

        if (diffPrice > 0) {
            log.info("Цена увеличилась, +{}, товар: {}", diffPrice, currentTitle);
        } else if (diffPrice < 0) {
            log.info("Цена упала, {}, товар: {}", diffPrice, currentTitle);
        } else {
            log.info("Цена не изменилась, товар: {}", currentTitle);
        }
        return new PageResponse(currentPrice, diffPrice, currentTitle);
    }
}
