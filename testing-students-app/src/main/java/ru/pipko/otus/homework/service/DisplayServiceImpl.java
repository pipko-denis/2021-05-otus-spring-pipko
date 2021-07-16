package ru.pipko.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

@Service
public class DisplayServiceImpl implements DisplayService {


    private final PrintService printService;

    private final int minRightResponses;

    public DisplayServiceImpl(PrintService printService, @Value("${min-pass-count}") int minRightResponses){
        this.printService = printService;
        this.minRightResponses = minRightResponses;
    }

    @Override
    public void displayAnswers(List<Answer> answers)  {

        Answer answer;
        for (int i = 0; i < answers.size(); i++) {
            answer = answers.get(i);
            printService.printLn((i + 1) + ". " + answer);
        }
    }

    @Override
    public void displayInterviewResults(Interview interview) {
        printService.printLn("Student "+interview.getUserName() + " passing results... ");
        int cntRightAnswers = 0;
        for(Question question : interview.getQuestionList()){
            if ( (question.getPickedAnswer() != null) && (question.getPickedAnswer().getIsRightAnswer())){
                cntRightAnswers++;
            }
        }
        if (cntRightAnswers >= minRightResponses){
            this.printService.printLn("Congratulation, the test is passed! You have "+cntRightAnswers+" right answers.");
        } else{
            this.printService.printLn("Sorry, the test is not passed! You only have "+cntRightAnswers+" correct answers from "+this.minRightResponses+" needed to complete.");
        }


    }


}
