package ru.pipko.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application")
public class CsvCustomProperties {

    private final LocaleCustomProperties localeCustomProperties;

    private Map<String,String> localizedFiles;

    public CsvCustomProperties(LocaleCustomProperties localeCustomProperties){
        this.localeCustomProperties = localeCustomProperties;
    }

    public String getCsvFileName() {
        return localizedFiles.get(localeCustomProperties.getLocaleName());
    }

    public void setLocalizedFiles(Map<String, String> localizedFiles) {
        this.localizedFiles = localizedFiles;
    }


}
