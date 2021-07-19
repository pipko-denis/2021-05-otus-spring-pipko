package ru.pipko.otus.homework.service;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

public class DisplayQuestionsServiceImpl implements DisplayQuestionsService{

    private final PrintService printService;

    private final DisplayAnswersService displayAnswersService;

    public DisplayQuestionsServiceImpl(PrintService printService, DisplayAnswersService displayAnswersService){
        this.printService = printService;
        this.displayAnswersService = displayAnswersService;
    }

    @Override
    public void displayQuestions(List<Question> questionList) throws Exception {

        for(Question question : questionList){
            if (question == null) continue;

            printService.printLn(question.getText());

            displayAnswersService.displayAnswers(question.getAnswers());

        }

    }




}
