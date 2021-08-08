package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.domain.Student;

import java.util.List;

public interface DisplayService {

    void displayInterviewResults(Student student, List<Question> questions);

}
