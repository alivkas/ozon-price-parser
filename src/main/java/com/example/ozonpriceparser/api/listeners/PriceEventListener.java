package com.example.ozonpriceparser.api.listeners;

import com.example.ozonpriceparser.api.events.PriceEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Slf4j
public class PriceEventListener {
    String url;
    boolean isReadyToWork = false;

    @EventListener
    public void handlePriceEvent(PriceEvent event) {
        url = event.url();
        if (!url.isEmpty()) {
            isReadyToWork = true;
            log.info("{}, успешно сохранено", url);
        } else {
            log.info("Ссылка не найдена");
        }
    }
}
