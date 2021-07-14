package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

public class InterviewServiceImpl implements InterviewService{

    private final QuestionDao questionDao;

    private final DisplayQuestionsService displayQuestionsService;

    private final ValidateQuestionService validateQuestionService;

    private final PrintService printService;


    public InterviewServiceImpl(QuestionDao questionDao, PrintService printService
            , DisplayQuestionsService displayQuestionsService, ValidateQuestionService validateQuestionService){
        this.questionDao = questionDao;
        this.printService = printService;
        this.displayQuestionsService = displayQuestionsService;
        this.validateQuestionService = validateQuestionService;

    }

    public void takeAnInterview() {
        List<Question> questionList = null;
        try {
            questionList = questionDao.getQuestions();
        } catch (QuestionsDaoException ex) {
            this.printService.printLn("Getting questions error: "+ex.getMessage());
        }

        try {
            this.validateQuestionService.validateQuestionsList(questionList);
        } catch (ValidateQuestionException ex) {
            this.printService.printLn("Validating questions error: "+ex.getMessage());
        }

        try {
            this.displayQuestionsService.askQuestions(questionList);
        } catch (Exception ex) {
            this.printService.printLn("Displaying questions error: "+ex.getMessage());
        }


    }





}
