package ru.pipko.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final CustomProperties customProperties;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(CustomProperties customProperties, MessageSource messageSource){
        this.customProperties = customProperties;
        this.messageSource = messageSource;
    }

    @Override
    public String localizeMessage(String messageName, String[] args) {
        return messageSource.getMessage(messageName,args, Locale.forLanguageTag(customProperties.getLocale()));
    }


}
