package ru.pipko.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.domain.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisplayServiceImpl implements DisplayService {


    private final int minRightResponses;

    private final PrintLocalizedMessagesServiceImpl printLocalizedMessagesService;


    @Override
    public void displayInterviewResults(Student student, List<Question> questions) {
        printLocalizedMessagesService.printLocalizedMessage("strings.passing.results", student.getFullName());
        int cntRightAnswers = 0;
        for (Question question : questions) {
            if ((question.getPickedAnswer() != null) && (question.getPickedAnswer().getIsRightAnswer())) {
                cntRightAnswers++;
            }
        }

        printLocalizedMessagesService.printLocalizedMessage(
                (cntRightAnswers >= minRightResponses) ? "strings.congrats" : "strings.sorry"
                , String.valueOf(cntRightAnswers),String.valueOf(minRightResponses));



    }


}
