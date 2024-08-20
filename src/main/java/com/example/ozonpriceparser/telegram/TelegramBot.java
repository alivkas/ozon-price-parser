package com.example.ozonpriceparser.telegram;

import com.example.ozonpriceparser.config.property.TelegramBotProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBot extends TelegramLongPollingBot {

    TelegramBotProperties properties;

    @Override
    public String getBotUsername() {
        return properties.name();
    }

    @Override
    public String getBotToken() {
        return properties.token();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
