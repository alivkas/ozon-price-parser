package com.example.ozonpriceparser.api.service;

import com.example.ozonpriceparser.api.PriceSerial;
import com.example.ozonpriceparser.api.dto.PageDto;
import com.example.ozonpriceparser.api.dto.PageResponse;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;

import java.io.IOException;

public interface PriceService {
    PageDto getPrice(String url) throws ElementNotFoundException;
    void serializePrice() throws ElementNotFoundException, IOException;
    PriceSerial deserializePrice() throws IOException, ClassNotFoundException, ElementNotFoundException;
    PageResponse getDifferencePrice(String url) throws IOException, ClassNotFoundException, ElementNotFoundException;
}
