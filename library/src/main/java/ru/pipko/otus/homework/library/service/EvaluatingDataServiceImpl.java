package ru.pipko.otus.homework.library.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EvaluatingDataServiceImpl implements EvaluatingDataService{

    private final static Pattern patternPositiveInt = Pattern.compile("\\d+");


    @Override
    public boolean isThereAreOnlyDigitsInText(String text) {
        if (text == null) {
            return false;
        }
        return patternPositiveInt.matcher(text).matches();
    }

    @Override
    public boolean isTextNotNullAndNotBlank(String text) {
        return (text != null) && (! text.isBlank());
    }

}
