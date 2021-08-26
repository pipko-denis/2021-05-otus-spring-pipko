package ru.pipko.otus.homework.library.service;

import org.springframework.stereotype.Service;

@Service
public class EvaluatingDataServiceImpl implements EvaluatingDataService{
    @Override
    public boolean isThereAreOnlyDigitsInText(String text) {
        return false;
    }
}
