package com.example.ozonpriceparser.api.events;

public record PriceEvent(String url,
                         String title,
                         Integer price) {
}
