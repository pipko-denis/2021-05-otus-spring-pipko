package ru.pipko.otus.homework.events;

import ru.pipko.otus.homework.domain.Student;

public interface EventsPublisher {

    void publishUserAssigned(Student student);

    void publishQuestionsPassed(int cntPassed, int cntTotal);

}
