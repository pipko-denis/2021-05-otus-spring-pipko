package ru.pipko.otus.homework.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class QuestionPartiallyPassedEvent extends ApplicationEvent {

    private final int countPassed;

    private final int countTotal;

    public QuestionPartiallyPassedEvent(Object source, int countPassed, int countTotal) {
        super(source);
        this.countPassed = countPassed;
        this.countTotal = countTotal;
    }
}
