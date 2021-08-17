package ru.pipko.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.events.PublisherForEvents;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskQuestionsServiceImpl implements AskQuestionsService {

    private final PrintLocalizedMessagesService printLocalizedMessagesService;

    private final PublisherForEvents publisherForEvents;

    private final ReadAnswerOnQuestionService readAnswerOnQuestionService;



    @Override
    public void askQuestions(List<Question> questionList) {

        for (int i = 0, j = 1, questionsCount = questionList.size() ; i < questionsCount; i++, j++) {
            Question question = questionList.get(i);

            printLocalizedMessagesService.printLocalizedMessage("strings.question",String.valueOf(j),question.getText());

            displayAnswers(question.getAnswers());

            getAnswerOnQuestion(question);

            publisherForEvents.publishQuestionsPassed(j, questionsCount);
        }
    }

    private void displayAnswers(List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            printLocalizedMessagesService.printLocalizedMessage("strings.answer",String.valueOf(i + 1),answer.getText());
        }
    }

    private void getAnswerOnQuestion(Question question){
        Answer answer = readAnswerOnQuestionService.readAnswerForQuestion(question);

        question.setPickedAnswer(answer);
    }




}
