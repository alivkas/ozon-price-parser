package com.example.ozonpriceparser.api.service;

import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;

import java.io.IOException;

public interface PriceService {
    Integer getPrice(String url) throws ElementNotFoundException;
    void serializePrice() throws ElementNotFoundException, IOException;
    Integer deserializePrice() throws IOException, ClassNotFoundException;
    Integer getDifferencePrice();
}
