package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;

@Service
public class DisplayServiceImpl implements DisplayService {


    private final PrintService printService;

    private final int minRightResponses;

    private final PrintLocalizedMessagesServiceImpl printLocalizedMessagesService;

    public DisplayServiceImpl(PrintService printService, CustomProperties customProperties, PrintLocalizedMessagesServiceImpl printLocalizedMessagesService) {
        this.printService = printService;
        this.minRightResponses = customProperties.getMinPassCount();
        this.printLocalizedMessagesService = printLocalizedMessagesService;
    }

    @Override
    public void displayInterviewResults(Interview interview) {
        printLocalizedMessagesService.printLocalizedMessage("strings.passing.results", interview.getStudent().getFullName());
        int cntRightAnswers = 0;
        for (Question question : interview.getQuestionList()) {
            if ((question.getPickedAnswer() != null) && (question.getPickedAnswer().getIsRightAnswer())) {
                cntRightAnswers++;
            }
        }

        printLocalizedMessagesService.printLocalizedMessage(
                (cntRightAnswers >= minRightResponses) ? "strings.congrats" : "strings.sorry"
                , String.valueOf(cntRightAnswers),String.valueOf(minRightResponses));



    }


}
