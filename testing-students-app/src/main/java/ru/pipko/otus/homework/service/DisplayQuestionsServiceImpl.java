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
    public void displayQuestions(List<Question> questionList) {

        Question question;

        for (int i = 0; i < questionList.size(); i++) {
            question = questionList.get(i);

            printService.printLn("Question #" + i + ": " + question.getText());

            displayAnswersService.displayAnswers(question.getAnswers());

        }


    }







}
