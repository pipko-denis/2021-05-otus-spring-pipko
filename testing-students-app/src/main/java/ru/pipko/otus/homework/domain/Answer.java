package ru.pipko.otus.homework.domain;

public class Answer {

    private String text;

    public Answer(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
