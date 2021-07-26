package ru.pipko.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

import java.util.List;

@Service
public class AskQuestionsServiceImpl implements AskQuestionsService {

    private final PrintService printService;

    private final ReadService readService;

    private final ValidateUserResponseService validateUserResponseService;

    private final int maxAttempts;


    public AskQuestionsServiceImpl(PrintService printService,
                                   ReadService readAnswerService, ValidateUserResponseService validateUserResponseService,
                                   @Value("${ask-questions-max-attempts}") int maxAttempts) {
        this.printService = printService;
        this.readService = readAnswerService;
        this.validateUserResponseService = validateUserResponseService;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void askQuestions(List<Question> questionList) {
        Question question;
        for (int i = 0; i < questionList.size(); i++) {
            question = questionList.get(i);
            printService.printLn("Question #" + (i + 1) + ": " + question.getText());

            displayAnswers(question.getAnswers());

            question.setPickedAnswer(readAnswer(question));
        }
    }


    private void displayAnswers(List<Answer> answers) {
        Answer answer;
        for (int i = 0; i < answers.size(); i++) {
            answer = answers.get(i);
            printService.printLn((i + 1) + ". " + answer.getText());
        }
    }

    @Override
    public String askSomething(String question) {
        printService.printLn(question);
        return readService.readInput();
    }


    private Answer readAnswer(Question question) {

        for (int i = 0; i < maxAttempts; i++) {
            printService.printLn("Please, enter answer's # from 1 to " + question.getAnswers().size() + " (attempt # " + (i + 1) + ")");

            String userResponse = readService.readInput();

            if (validateUserResponseService.isUserResponseIsValid(question, userResponse)) {
                int chosenIndex = Integer.parseInt(userResponse) - 1;
                return question.getAnswers().get(chosenIndex);
            }
        }

        return null;
    }


}
