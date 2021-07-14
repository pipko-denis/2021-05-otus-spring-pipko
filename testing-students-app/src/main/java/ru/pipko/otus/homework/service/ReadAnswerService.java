package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

public interface ReadAnswerService {

    Answer readResponse(Question question );

}
