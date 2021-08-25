package ru.pipko.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;

import static org.mockito.Mockito.when;

@SpringBootTest()
class ReadAnswerOnQuestionServiceTest {


    @Autowired
    ReadAnswerOnQuestionService readAnswerOnQuestionService;

//    @MockBean
//    CustomProperties customProperties;

    @MockBean
    ReadAnswerService readAnswerService;


    @Test
    void readAnswer() {
        Question question = new Question("Question 1",new String[]{"Answer 123::false","Answer 234::true","Answer 345::false"});

        when(readAnswerService.readAnswer("strings.enter.answer.request",
                String.valueOf(question.getAnswers().size()),"1"))
                .thenReturn("1");

        String result = readAnswerService.readAnswer("strings.enter.answer.request","1","1");

        System.out.println("RESULT: "+result);

        Answer answer = readAnswerOnQuestionService.readAnswerForQuestion(question);

        Assertions.assertEquals(0,question.getAnswers().indexOf(answer));

    }
}