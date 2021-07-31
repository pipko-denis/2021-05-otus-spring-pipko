package ru.pipko.otus.homework.service;

public class PrintLocalizedMessagesServiceImpl implements PrintLocalizedMessagesService {

    private final PrintService printService;

    private final LocalizationService localizationService;

    public PrintLocalizedMessagesServiceImpl(PrintService printService, LocalizationService localizationService) {
        this.printService = printService;
        this.localizationService = localizationService;
    }


    @Override
    public void printLocalizedMessage(String messageId, String... args) {
        String message = localizationService.localizeMessage(messageId, args);
        printService.printLn(message);
    }
}
