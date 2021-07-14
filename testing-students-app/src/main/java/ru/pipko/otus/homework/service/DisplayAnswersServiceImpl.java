package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Answer;

import java.util.List;

public class DisplayAnswersServiceImpl implements DisplayAnswersService{


    private final PrintService printService;

    public DisplayAnswersServiceImpl(PrintService printService){
        this.printService = printService;
    }

    @Override
    public void displayAnswers(List<Answer> answers)  {

        Answer answer;
        for (int i = 0; i < answers.size(); i++) {
            answer = answers.get(i);
            printService.printLn((i + 1) + ". " + answer);
        }
    }


}
