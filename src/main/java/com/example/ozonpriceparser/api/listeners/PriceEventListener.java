package com.example.ozonpriceparser.api.listeners;

import com.example.ozonpriceparser.api.events.PriceEvent;
import com.example.ozonpriceparser.api.service.PriceService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
@Slf4j
public class PriceEventListener {
    PriceService priceServiceImpl;

    @NonFinal
    String url;
    @NonFinal
    Integer price;
    @NonFinal
    boolean isReadyToWork = false;

    @EventListener
    public void handlePriceEvent(PriceEvent event) {
        url = event.url();
        price = event.price();
        if (!url.isEmpty()) {
            isReadyToWork = true;
            log.info("{}, успешно сохранено", url);
        } else {
            log.info("Ссылка не найдена");
        }
    }
}
