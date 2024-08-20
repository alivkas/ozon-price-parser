package com.example.ozonpriceparser.config;

import com.example.ozonpriceparser.config.property.ElementsProperties;
import com.example.ozonpriceparser.config.property.TelegramBotProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ElementsProperties.class, TelegramBotProperties.class})
public class EnableConfProperties {
}
