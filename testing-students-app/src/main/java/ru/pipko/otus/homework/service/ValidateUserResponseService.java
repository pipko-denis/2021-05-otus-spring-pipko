package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;

public interface ValidateUserResponseService {

    boolean isUserResponseIsValid(Question question, String response);

}
