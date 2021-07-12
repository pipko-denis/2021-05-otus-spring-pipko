package ru.pipko.otus.homework;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.io.IOException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest
{


    @Test
    public void isFileWithQuestionsUnreachableOrEmpty()  {
        String fileName = "questions.csv";

        QuestionDao dao = new QuestionCsvDao(fileName);

        List<Question> questionList = null;
        try {
            questionList = dao.getQuestions();
        } catch (QuestionsDaoException e) {
            e.printStackTrace();
        }

        Assert.isTrue(questionList != null,"Questions list in file \""+fileName+"\" is null");

        Assert.isTrue( ! questionList.isEmpty()  ,"Questions list in file \""+fileName+"\" is empty");
    }

}
