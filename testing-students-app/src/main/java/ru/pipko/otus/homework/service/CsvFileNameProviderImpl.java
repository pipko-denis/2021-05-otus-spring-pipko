package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.config.CustomProperties;

@Component
public class CsvFileNameProviderImpl implements CsvFileNameProvider{

    private final CustomProperties customProperties;

    public CsvFileNameProviderImpl(CustomProperties customProperties){
        this.customProperties = customProperties;
    }

    @Override
    public String getCsvFileName() {
        return customProperties.getCsvFileName();
    }
}
