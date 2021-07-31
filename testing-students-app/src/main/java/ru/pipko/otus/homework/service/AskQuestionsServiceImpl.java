package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

@Service
public class AskQuestionsServiceImpl implements AskQuestionsService {

    private final PrintService printService;

    private final ReadService readService;

    private final ValidateUserResponseService validateUserResponseService;

    private final int maxAttempts;

    private final PrintLocalizedMessagesService printLocalizedMessagesService;

    private final ReadAnswerService readAnswerService;


    public AskQuestionsServiceImpl(PrintService printService,
                                   ReadService readAnswerService, ValidateUserResponseService validateUserResponseService,
                                   CustomProperties customProperties, PrintLocalizedMessagesService printLocalizedMessagesService,
                                   ReadAnswerService readAnswerService1) {
        this.printService = printService;
        this.readService = readAnswerService;
        this.validateUserResponseService = validateUserResponseService;
        this.maxAttempts = customProperties.getAskQuestionsMaxAttempts();
        this.printLocalizedMessagesService = printLocalizedMessagesService;
        this.readAnswerService = readAnswerService1;
    }

    @Override
    public void askQuestions(List<Question> questionList) {

        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);

            printLocalizedMessagesService.printLocalizedMessage("strings.question",String.valueOf(i + 1),question.getText());

            displayAnswers(question.getAnswers());

            question.setPickedAnswer(readAnswer(question));
        }
    }


    private void displayAnswers(List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            printLocalizedMessagesService.printLocalizedMessage("strings.answer",String.valueOf(i + 1),answer.getText());
        }
    }


    private Answer readAnswer(Question question) {

        String answersCount = String.valueOf(question.getAnswers().size() );
        for (int i = 0; i < maxAttempts; i++) {

            String userResponse = readAnswerService.readAnswerForQuestion("strings.enter.answer.request",answersCount,String.valueOf(i + 1));

            if (validateUserResponseService.isUserResponseIsValid(question, userResponse)) {
                int chosenIndex = Integer.parseInt(userResponse) - 1;
                return question.getAnswers().get(chosenIndex);
            }
        }

        return null;
    }


}
