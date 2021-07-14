package ru.pipko.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

import java.io.InputStream;
import java.util.Scanner;

public class ReadAnswerServiceImpl implements ReadAnswerService {

    private final Scanner scanner;

    private final PrintService printService;

    private final ValidateUserResponseService validateUserResponseService;

    private final int maxAttempts;



    public ReadAnswerServiceImpl(InputStream inputStream, PrintService printService, ValidateUserResponseService validateUserResponseService
            , int maxAttempts){
        this.scanner = new Scanner(inputStream);
        this.printService = printService;
        this.validateUserResponseService = validateUserResponseService;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Answer readResponse(Question question ) {

        int chosenIndex;
        String userResponce;
        for (int i=0; i < maxAttempts; i++){
            printService.printLn("Please, enter answer's # from 1 to "+question.getAnswers().size());

            userResponce = scanner.nextLine();

            if ( this.validateUserResponseService.isUserResponseIsValid(question,userResponce) ) {
                chosenIndex = Integer.parseInt(userResponce);
                return question.getAnswers().get(chosenIndex);
            }
        }

        return null;
    }
}
