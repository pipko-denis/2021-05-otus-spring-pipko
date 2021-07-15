package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

import java.io.InputStream;
import java.util.Scanner;

public class ReadUserResponseServiceImpl implements ReadUserResponseService {

    private final Scanner scanner;

    private final PrintService printService;

    private final ValidateUserResponseService validateUserResponseService;

    private final int maxAttempts;



    public ReadUserResponseServiceImpl(InputStream inputStream, PrintService printService, ValidateUserResponseService validateUserResponseService
            , int maxAttempts){
        this.scanner = new Scanner(inputStream);
        this.printService = printService;
        this.validateUserResponseService = validateUserResponseService;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Answer readAnswer(Question question ) {

        int chosenIndex;
        String userResponce;
        for (int i=0; i < maxAttempts; i++){
            printService.printLn("Please, enter answer's # from 1 to "+question.getAnswers().size()+" (attempt # "+(i+1)+")");

            userResponce = scanner.nextLine();

            if ( this.validateUserResponseService.isUserResponseIsValid(question,userResponce) ) {
                chosenIndex = Integer.parseInt(userResponce)-1;
                return question.getAnswers().get(chosenIndex);
            }
        }

        return null;
    }



    @Override
    public String readUserInput() {
        return scanner.nextLine();
    }
}
