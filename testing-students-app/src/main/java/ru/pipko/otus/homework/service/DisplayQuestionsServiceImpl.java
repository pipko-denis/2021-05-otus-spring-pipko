package ru.pipko.otus.homework.service;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

public class DisplayQuestionsServiceImpl implements DisplayQuestionsService{

    private final PrintService printService;

    private final DisplayAnswersService displayAnswersService;

    private final ReadAnswerService readAnswerService;


    public DisplayQuestionsServiceImpl(PrintService printService, DisplayAnswersService displayAnswersService, ReadAnswerService readAnswerService, ValidateUserResponseService validateUserResponceService){
        this.printService = printService;
        this.displayAnswersService = displayAnswersService;
        this.readAnswerService = readAnswerService;
    }

    @Override
    public void askQuestions(List<Question> questionList) {

        Question question;
        for (int i = 0; i < questionList.size(); i++) {
            question = questionList.get(i);

            printService.printLn("Question #" + i + ": " + question.getText());

            displayAnswersService.displayAnswers(question.getAnswers());

            question.setPickedAnswer(readAnswerService.readResponse(question));

        }


    }







}
