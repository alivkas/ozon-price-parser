package com.example.ozonpriceparser.api.service;

import com.example.ozonpriceparser.api.errors.exceptions.ElementNotFoundException;

public interface PriceService {
    Integer getPrice(String url) throws ElementNotFoundException;
    Integer priceToInt(String price);
}
