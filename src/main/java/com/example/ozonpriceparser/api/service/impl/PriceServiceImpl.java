package com.example.ozonpriceparser.api.service.impl;

import com.example.ozonpriceparser.config.property.ElementsProperties;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import com.example.ozonpriceparser.api.service.PriceService;
import com.microsoft.playwright.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {
    ElementsProperties elementsProperties;

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

    @Override
    public Integer priceToInt(String price) {
        String normalPrice = price
                .replace("₽", "")
                .replace(" ", "");
        return Integer.parseInt(normalPrice);
    }
}
