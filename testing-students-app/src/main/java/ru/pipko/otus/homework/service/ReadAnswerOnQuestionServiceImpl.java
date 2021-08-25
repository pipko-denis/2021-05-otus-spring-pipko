package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

@Service
public class ReadAnswerOnQuestionServiceImpl implements ReadAnswerOnQuestionService {

    private final int maxAttempts;

    private final ValidateUserResponseService validateUserResponseService;

    private final ReadAnswerService readAnswerService;

    public ReadAnswerOnQuestionServiceImpl(CustomProperties customProperties, ValidateUserResponseService validateUserResponseService,
                                           ReadAnswerService readAnswerService) {
        this.maxAttempts = customProperties.getAskQuestionsMaxAttempts();
        this.validateUserResponseService = validateUserResponseService;
        this.readAnswerService = readAnswerService;
    }


    @Override
    public Answer readAnswerForQuestion(Question question) {
        String answersCount = String.valueOf(question.getAnswers().size() );
        for (int i = 0; i < maxAttempts; i++) {

            String userResponse = readAnswerService.readAnswer("strings.enter.answer.request",answersCount,String.valueOf(i + 1));

            if (validateUserResponseService.isUserResponseIsValid(question, userResponse)) {
                int chosenIndex = Integer.parseInt(userResponse) - 1;
                return question.getAnswers().get(chosenIndex);
            }
        }

        return null;
    }
}
