package com.example.ozonpriceparser.web;

import com.example.ozonpriceparser.api.dto.PageDto;
import com.example.ozonpriceparser.api.dto.PageResponse;
import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import com.example.ozonpriceparser.api.service.PriceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/current-price")
public class PriceController {
    PriceService priceServiceImpl;

    @PostMapping()
    public PageResponse getCurrentPrice(@Valid @RequestBody PageDto pageDto) throws ElementNotFoundException, IOException, ClassNotFoundException {
        return priceServiceImpl.getDifferencePrice(pageDto.url());
    }
}
