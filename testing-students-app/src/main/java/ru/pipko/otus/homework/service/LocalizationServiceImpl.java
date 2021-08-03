package ru.pipko.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.LocaleCustomProperties;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final LocaleCustomProperties localeCustomProperties;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(LocaleCustomProperties localeCustomProperties, MessageSource messageSource){
        this.localeCustomProperties = localeCustomProperties;
        this.messageSource = messageSource;
    }

    @Override
    public String localizeMessage(String messageName, String... args) {
        return messageSource.getMessage(messageName,args, localeCustomProperties.getLocale());
    }


}
