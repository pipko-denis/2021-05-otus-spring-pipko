package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;

@Component
@RequiredArgsConstructor
@Profile("calm")
public class ListenerEventsAnnotated {

    private final PrintLocalizedMessagesService printService;

    @EventListener
    public void questionPartiallyPassedEventListener(EventQuestionPartiallyPassed event){
        printService.printLocalizedMessage("strings.events.progress",
                String.valueOf(event.getCountPassed()),String.valueOf(event.getCountTotal()));
    }

    @EventListener
    public void userAssignedEventListener(EventUserAssigned eventUserAssigned){
        printService.printLocalizedMessage("strings.events.user.calm",
                String.valueOf(eventUserAssigned.getStudent().getFullName()));
    }

}
