package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;

@Component
@RequiredArgsConstructor
public class UserAssignedEventListener implements ApplicationListener<UserAssignedEvent> {

    private final PrintLocalizedMessagesService printService;

    @Override
    public void onApplicationEvent(UserAssignedEvent userAssignedEvent) {
        printService.printLocalizedMessage("strings.events.user.fun",
                String.valueOf(userAssignedEvent.getStudent().getFullName()));
    }
}
