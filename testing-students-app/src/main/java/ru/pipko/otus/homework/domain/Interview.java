package ru.pipko.otus.homework.domain;

import java.util.List;

public class Interview {

    private final Student student;

    private final List<Question> questionList;

    public Interview(Student student, List<Question> questionList) {
        this.student = student;
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public Student getStudent() {
        return student;
    }
}
