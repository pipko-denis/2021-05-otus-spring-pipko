package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;

@Component
@RequiredArgsConstructor
public class ListenerUserAssignedEvent implements ApplicationListener<EventUserAssigned> {

    private final PrintLocalizedMessagesService printService;

    @Override
    public void onApplicationEvent(EventUserAssigned eventUserAssigned) {
        printService.printLocalizedMessage("strings.events.user.fun",
                String.valueOf(eventUserAssigned.getStudent().getFullName()));
    }
}
