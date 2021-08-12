package ru.pipko.otus.homework.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.pipko.otus.homework.domain.Student;

@Getter
public class EventUserAssigned extends ApplicationEvent {

    private final Student student;

    public EventUserAssigned(Object source, Student student) {
        super(source);
        this.student = student;
    }



}
