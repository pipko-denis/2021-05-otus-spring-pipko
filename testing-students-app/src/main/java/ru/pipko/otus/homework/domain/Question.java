package ru.pipko.otus.homework.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private final String text;

    private final List<Answer> answers;

    private Answer pickedAnswer = null;

    public Question(String text, String[] answers) {
        this.text = text;
        this.answers = new ArrayList<>();
        for (String answerText : answers) {
            this.answers.add(new Answer(answerText));
        }
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getPickedAnswer() {
        return pickedAnswer;
    }

    public void setPickedAnswer(Answer pickedAnswer) {
        this.pickedAnswer = pickedAnswer;
    }


}
