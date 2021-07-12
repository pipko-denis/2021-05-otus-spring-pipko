package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;

import java.io.IOException;
import java.util.List;

public class InterviewServiceImpl implements InterviewService{

    private final QuestionDao questionDao;

    private final DisplayQuestionsService displayQuestionsService;

    private final PrintService printService;


    public InterviewServiceImpl(QuestionDao questionDao, PrintService printService, DisplayQuestionsService displayQuestionsService){
        this.questionDao = questionDao;
        this.printService = printService;
        this.displayQuestionsService = displayQuestionsService;
    }

    public void takeAnInterview() {
        List<Question> questionList = null;
        try {
            questionList = questionDao.getQuestions();
        } catch (IOException ex) {
            this.printService.printLn("Getting questions error: "+ex.getMessage());
        }

        try {
            this.displayQuestionsService.displayQuestions(questionList);
        } catch (Exception ex) {
            this.printService.printLn("Displaying questions error: "+ex.getMessage());
        }


    }





}
