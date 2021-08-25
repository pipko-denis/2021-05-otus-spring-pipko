package ru.pipko.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application")
public class CustomProperties {

    private int askQuestionsMaxAttempts;
    private int minPassCount;
    private Map<String,String> localizedFiles;
    private String localeName;

    public int getAskQuestionsMaxAttempts() {
        return askQuestionsMaxAttempts;
    }

    public void setAskQuestionsMaxAttempts(int askQuestionsMaxAttempts) {
        this.askQuestionsMaxAttempts = askQuestionsMaxAttempts;
    }

    public int getMinPassCount() {
        return minPassCount;
    }

    public void setMinPassCount(int minPassCount) {
        this.minPassCount = minPassCount;
    }




    public String getLocaleName() {
        return localeName;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(localeName);
    }

    public void setLocaleName(String locale) {
        this.localeName = locale;
    }



    public String getCsvFileName() {
        return localizedFiles.get(localeName);
    }

    public void setLocalizedFiles(Map<String, String> localizedFiles) {
        this.localizedFiles = localizedFiles;
    }
}
