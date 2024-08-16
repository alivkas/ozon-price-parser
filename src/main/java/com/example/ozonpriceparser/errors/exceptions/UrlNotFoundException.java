package com.example.ozonpriceparser.errors.exceptions;

public class UrlNotFoundException extends Exception {
    public final String CODE = "400";

    public UrlNotFoundException(String url) {
        super("Url not found " + url);
    }
}
