package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;

@Service
public class DisplayServiceImpl implements DisplayService {


    private final PrintService printService;

    private final int minRightResponses;

    private final LocalizationService localizationService;

    public DisplayServiceImpl(PrintService printService, CustomProperties customProperties, LocalizationService localizationService) {
        this.printService = printService;
        this.minRightResponses = customProperties.getMinPassCount();
        this.localizationService = localizationService;
    }

    @Override
    public void displayInterviewResults(Interview interview) {
        String message = localizationService.localizeMessage("strings.passing.results", new String[]{interview.getStudent().getFullName()});
        printService.printLn(message);
        int cntRightAnswers = 0;
        for (Question question : interview.getQuestionList()) {
            if ((question.getPickedAnswer() != null) && (question.getPickedAnswer().getIsRightAnswer())) {
                cntRightAnswers++;
            }
        }

        message = localizationService.localizeMessage(
                (cntRightAnswers >= minRightResponses) ? "strings.congrats" : "strings.sorry"
                , new String[]{String.valueOf(cntRightAnswers)});

        printService.printLn(message);


    }


}
