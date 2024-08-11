package com.example.ozonpriceparser.api.scheduling;

import com.example.ozonpriceparser.api.listeners.PriceEventListener;
import com.example.ozonpriceparser.api.service.PriceService;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PriceTrackingScheduling {

    PriceService priceServiceImpl;
    PriceEventListener priceEventListener;

    @Scheduled(fixedDelay = 5000)
    public void trackingPrice() throws ElementNotFoundException, IOException, InterruptedException, ClassNotFoundException {
        if (priceEventListener.isReadyToWork()) {
            priceServiceImpl.serializePrice(priceEventListener.getUrl());
            log.info("Объект сериализован");
        }
    }
}
