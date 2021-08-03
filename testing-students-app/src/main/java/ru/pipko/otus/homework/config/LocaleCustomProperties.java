package ru.pipko.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "application")
public class LocaleCustomProperties {

    private String localeName;

    public String getLocaleName() {
        return localeName;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(localeName);
    }

    public void setLocaleName(String locale) {
        this.localeName = locale;
    }


}
