package ru.pipko.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"application.events.show-half-questions"}, havingValue = "true")
@Profile("calm")
public class ListenerQuestionPartiallyPassed implements ApplicationListener<EventQuestionPartiallyPassed> {

    private final PrintLocalizedMessagesService printService;


    @Override
    public void onApplicationEvent(EventQuestionPartiallyPassed event) {
        printService.printLocalizedMessage("strings.events.progress",
                String.valueOf(event.getCountPassed()), String.valueOf(event.getCountTotal()));
    }
}
