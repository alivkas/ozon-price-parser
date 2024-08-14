package com.example.ozonpriceparser.api;

import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
@Setter
public class PriceSerial implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Integer price;
    String title;
}
