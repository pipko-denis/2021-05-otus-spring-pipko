package ru.pipko.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application")
public class CustomProperties {

    private String csvFileName;
    private int askQuestionsMaxAttempts;
    private int minPassCount;
    private Map<String,String> localizedFiles;
    private String locale;

    public String getCsvFileName() {
        return localizedFiles.get(locale);
        //return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

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

    public Map<String, String> getLocalizedFiles() {
        return localizedFiles;
    }

    public void setLocalizedFiles(Map<String, String> localizedFiles) {
        this.localizedFiles = localizedFiles;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
