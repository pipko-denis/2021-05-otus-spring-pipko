package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;

@Component
@RequiredArgsConstructor
public class EventsAnnotatedListener {

    private final PrintLocalizedMessagesService printService;

    @EventListener
    public void questionPartiallyPassedEventListener(QuestionPartiallyPassedEvent event){
        printService.printLocalizedMessage("strings.events.progress",
                String.valueOf(event.getCountPassed()),String.valueOf(event.getCountTotal()));
    }

    @EventListener
    public void userAssignedEventListener(UserAssignedEvent userAssignedEvent){
        printService.printLocalizedMessage("strings.events.user.calm",
                String.valueOf(userAssignedEvent.getStudent().getFullName()));
    }

}
