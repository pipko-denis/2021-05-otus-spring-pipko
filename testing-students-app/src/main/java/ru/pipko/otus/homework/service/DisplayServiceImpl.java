package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;

@Service
public class DisplayServiceImpl implements DisplayService {


    private final PrintService printService;

    private final int minRightResponses;

    public DisplayServiceImpl(PrintService printService, CustomProperties customProperties){
        this.printService = printService;
        this.minRightResponses = customProperties.getMinPassCount();
    }

    @Override
    public void displayInterviewResults(Interview interview) {
        printService.printLn("Student "+interview.getStudent().getFullName() + " passing results... ");
        int cntRightAnswers = 0;
        for(Question question : interview.getQuestionList()){
            if ( (question.getPickedAnswer() != null) && (question.getPickedAnswer().getIsRightAnswer())){
                cntRightAnswers++;
            }
        }
        if (cntRightAnswers >= minRightResponses){
            printService.printLn("Congratulation, the test is passed! You have "+cntRightAnswers+" right answers.");
        } else{
            printService.printLn("Sorry, the test is not passed! You only have "+cntRightAnswers+" correct answers from "+this.minRightResponses+" needed to complete.");
        }


    }


}
