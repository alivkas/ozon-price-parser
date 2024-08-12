package com.example.ozonpriceparser.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "element")
public record ElementsProperties(String elementPrice,
                                 String elementTitle,
                                 String userAgent) {

}
