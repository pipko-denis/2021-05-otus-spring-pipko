package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;

@Service
public class ReadAnswerServiceImpl implements ReadAnswerService{

    private final PrintLocalizedMessagesServiceImpl printLocalizedMessagesService;

    private final ReadService readService;

    public ReadAnswerServiceImpl(PrintLocalizedMessagesServiceImpl printLocalizedMessagesService, ReadService readService){
        this.printLocalizedMessagesService = printLocalizedMessagesService;
        this.readService = readService;
    }


    @Override
    public String readAnswerForQuestion(String messageId, String... args) {
        printLocalizedMessagesService.printLocalizedMessage(messageId,args);
        return readService.readInput();
    }
}
