package com.example.ozonpriceparser.api.errors.exceptions;

public class ElementNotFoundException extends Exception {
    public final String CODE = "500";
    public ElementNotFoundException(String element) {
        super("Element not found: " + element);
    }
}
