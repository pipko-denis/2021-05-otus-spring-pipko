package ru.pipko.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(LocaleProvider localeProvider, MessageSource messageSource){
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    public String localizeMessage(String messageName, String... args) {
        return messageSource.getMessage(messageName,args, localeProvider.getLocale());
    }


}
