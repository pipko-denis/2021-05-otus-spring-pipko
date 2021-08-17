package ru.pipko.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAnswerServiceImpl implements ReadAnswerService{

    private final PrintLocalizedMessagesServiceImpl printLocalizedMessagesService;

    private final ReadService readService;


    @Override
    public String readAnswer(String messageId, String... args) {
        printLocalizedMessagesService.printLocalizedMessage(messageId,args);
        return readService.readInput();
    }
}
