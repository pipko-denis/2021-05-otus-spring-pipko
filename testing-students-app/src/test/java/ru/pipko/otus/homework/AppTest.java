package ru.pipko.otus.homework;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.service.IQuestionExtractorService;
import ru.pipko.otus.homework.service.QuestionExtractorService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{


    @Test
    public void isFileWithQuestionsUnreachableOrEmpty() {
        String fileName = "questions.csv";

        IQuestionExtractorService extractorService = new QuestionExtractorService(fileName);

        List<Question> questionList = extractorService.getQuestions();

        Assert.isTrue(questionList != null,"Questions list in file \""+fileName+"\" is null");

        Assert.isTrue( ! questionList.isEmpty()  ,"Questions list in file \""+fileName+"\" is empty");
    }

}
