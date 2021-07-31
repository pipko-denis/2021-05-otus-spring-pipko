package ru.pipko.otus.homework.service;

public class ReadAnswerServiceImpl implements ReadAnswerService{

    private final PrintLocalizedMessagesServiceImpl printLocalizedMessagesService;

    private final ReadService readService;

    public ReadAnswerServiceImpl(PrintLocalizedMessagesServiceImpl printLocalizedMessagesService, ReadService readService){
        this.printLocalizedMessagesService = printLocalizedMessagesService;
        this.readService = readService;
    }


    @Override
    public String readAnswerForQuestion(String messageId, String... args) {
        printLocalizedMessagesService.printLocalizedMessage("strings.enter.answer.request",args);
        return readService.readInput();
    }
}
