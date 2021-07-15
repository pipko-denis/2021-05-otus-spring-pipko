package ru.pipko.otus.homework.service;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

public class AskQuestionsServiceImpl implements AskQuestionsService {

    private final PrintService printService;

    private final DisplayService displayService;

    private final ReadUserResponseService readAnswerService;


    public AskQuestionsServiceImpl(PrintService printService, DisplayService displayService, ReadUserResponseService readAnswerService){
        this.printService = printService;
        this.displayService = displayService;
        this.readAnswerService = readAnswerService;
    }

    @Override
    public void askQuestions(List<Question> questionList) {

        int i = 1;
        for ( Question question : questionList) {
            printService.printLn("Question #" + (i) + ": " + question.getText());

            displayService.displayAnswers(question.getAnswers());

            question.setPickedAnswer(readAnswerService.readAnswer(question));
            i++;
        }


    }

    @Override
    public String askSomething(String question) {
        printService.printLn(question);
        return readAnswerService.readUserInput();
    }




}
