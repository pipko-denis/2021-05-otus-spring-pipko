package ru.pipko.otus.homework.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventQuestionPartiallyPassed extends ApplicationEvent {

    private final int countPassed;

    private final int countTotal;

    public EventQuestionPartiallyPassed(Object source, int countPassed, int countTotal) {
        super(source);
        this.countPassed = countPassed;
        this.countTotal = countTotal;
    }
}
