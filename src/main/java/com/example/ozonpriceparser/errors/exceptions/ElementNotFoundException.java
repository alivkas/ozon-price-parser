package com.example.ozonpriceparser.errors.exceptions;

public class ElementNotFoundException extends Exception {
    public final String CODE = "500";
    public ElementNotFoundException(String elementPrice, String elementTitle) {
        super("Element not found: " + elementPrice + " --- " + elementTitle);
    }
}
