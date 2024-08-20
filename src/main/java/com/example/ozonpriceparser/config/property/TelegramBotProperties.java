package com.example.ozonpriceparser.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public record TelegramBotProperties(String token,
                                    String name) {
}
