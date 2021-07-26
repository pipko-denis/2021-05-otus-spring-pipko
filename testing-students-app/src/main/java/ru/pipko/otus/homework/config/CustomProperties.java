package ru.pipko.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class CustomProperties {

    private String csvFileName;
    private int askQuestionsMaxAttempts;
    private int minPassCount;

    public String getCsvFileName() {
        return csvFileName;
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
}
