package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;

import java.util.regex.Pattern;

public class ValidateUserResponseServiceImpl implements ValidateUserResponseService {


    @Override
    public boolean isUserResponseIsValid(Question question, String response) {
        final Pattern pattern = Pattern.compile("[1-"+question.getAnswers().size()+"]");
        return pattern.matcher(response).find();
    }
}
