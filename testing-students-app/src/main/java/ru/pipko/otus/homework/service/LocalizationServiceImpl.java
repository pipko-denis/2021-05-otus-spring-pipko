package ru.pipko.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;


    @Override
    public String localizeMessage(String messageName, String... args) {
        return messageSource.getMessage(messageName,args, localeProvider.getLocale());
    }


}
