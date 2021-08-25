package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Student;

@Service
@RequiredArgsConstructor
public class PublisherForEventsImpl implements PublisherForEvents {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishUserAssigned(Student student) {
        eventPublisher.publishEvent(new EventUserAssigned(this,student));
    }

    @Override
    public void publishQuestionsPassed(int cntPassed, int cntTotal) {
        if (cntPassed == cntTotal/2 )  eventPublisher.publishEvent(new EventQuestionPartiallyPassed(this,cntPassed,cntTotal));
    }
}
