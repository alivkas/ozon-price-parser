package com.example.ozonpriceparser.api.service.impl;

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
    @Override
    public Integer getPrice(String url) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setUserAgent("Chrome/4.0.249.0 Safari/532.5"));

        Page page = browserContext.newPage();
        page.navigate(url);
        page.waitForTimeout(2000);

        ElementHandle elementHandle = page.querySelector("span.mn6_27.m6n_27.mo_27");
        String price = elementHandle.innerText();

        browser.close();

        return priceToInt(price);
    }

    @Override
    public Integer priceToInt(String price) {
        String normalPrice = price
                .replace("₽", "")
                .replace(" ", "");
        return Integer.parseInt(normalPrice);
    }
}
