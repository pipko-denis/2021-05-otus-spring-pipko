package ru.pipko.otus.homework.domain;

public class Answer {

    private String text;

    private Boolean isRightAnswer;

    public Answer(String text, Boolean isRightAnswer){
        this.text = text;
        this.isRightAnswer = isRightAnswer;
    }

    public String getText() {
        return text;
    }

    public Boolean getRightAnswer() {
        return isRightAnswer;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
