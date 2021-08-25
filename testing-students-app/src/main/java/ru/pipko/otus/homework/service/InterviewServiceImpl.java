package ru.pipko.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

@Service("interviewService")
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final QuestionDao questionDao;

    private final AskQuestionsService askQuestionsService;

    private final ValidateQuestionService validateQuestionService;

    private final PrintLocalizedMessagesService printLocalizedMessagesService;


    public List<Question> takeAnInterview() {
        List<Question> questionList = null;
        try {
            questionList = getQuestionsListFromRepository();

            validateQuestionsList(questionList);

            askQuestions(questionList);

        } catch (QuestionsDaoException ex) {
            printLocalizedMessagesService.printLocalizedMessage("strings.error.getting", ex.getMessage());
        } catch (ValidateQuestionException ex) {
            printLocalizedMessagesService.printLocalizedMessage("strings.error.validating", ex.getMessage());
        }

        return questionList;

    }

    private List<Question> getQuestionsListFromRepository() throws QuestionsDaoException {
        return questionDao.getQuestions();
    }

    private void validateQuestionsList(List<Question> questionList) throws ValidateQuestionException {
        validateQuestionService.validateQuestionsList(questionList);
    }

    private void askQuestions(List<Question> questionList) {
        askQuestionsService.askQuestions(questionList);
    }


}
