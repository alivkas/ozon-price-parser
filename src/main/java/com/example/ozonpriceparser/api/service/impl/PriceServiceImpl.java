package com.example.ozonpriceparser.api.service.impl;

import com.example.ozonpriceparser.api.Price;
import com.example.ozonpriceparser.api.events.PriceEvent;
import com.example.ozonpriceparser.api.listeners.PriceEventListener;
import com.example.ozonpriceparser.config.property.ElementsProperties;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import com.example.ozonpriceparser.api.service.PriceService;
import com.microsoft.playwright.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    ElementsProperties elementsProperties;
    ApplicationEventPublisher applicationEventPublisher;
    PriceEventListener priceEventListener;

    @Override
    public Integer getPrice(String url) throws ElementNotFoundException {
        Playwright playwright = Playwright.create();
        Browser browser = null;
        try {
            browser = playwright.chromium().launch();
            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setUserAgent(elementsProperties.userAgent()));

            Page page = browserContext.newPage();
            page.navigate(url);
            page.waitForTimeout(2000);

            ElementHandle elementHandle = page.querySelector(elementsProperties.page());

            if (browser != null && elementHandle != null) {
                String price = elementHandle.innerText();
                applicationEventPublisher.publishEvent(new PriceEvent(url, priceToInt(price)));
                return priceToInt(price);
            } else {
                throw new ElementNotFoundException(elementsProperties.page());
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException(elementsProperties.page());
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
    public void serializePrice() throws IOException {
        Price price = new Price(priceEventListener.getPrice());
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/price/serialize price.txt");
        ObjectOutputStream objectOutputStream =new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(price);
        objectOutputStream.close();
    }

    @Override
    public Integer deserializePrice() throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream("src/main/resources/price/serialize price.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Price price = (Price) objectInputStream.readObject();
        objectInputStream.close();
        return price.getPrice();
    }

    @Override
    public Integer getDifferencePrice() {
        return null;
    }
}
