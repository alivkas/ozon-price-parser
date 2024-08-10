package com.example.ozonpriceparser.api.service;

public interface PriceService {
    Integer getPrice(String url);
    Integer priceToInt(String price);
}
