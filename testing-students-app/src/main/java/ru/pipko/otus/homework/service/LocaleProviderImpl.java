package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.config.CustomProperties;

import java.util.Locale;

@Component
public class LocaleProviderImpl implements  LocaleProvider{

    private final CustomProperties customProperties;

    public LocaleProviderImpl(CustomProperties customProperties){
        this.customProperties = customProperties;
    }

    @Override
    public Locale getLocale() {
        return customProperties.getLocale();
    }
}
