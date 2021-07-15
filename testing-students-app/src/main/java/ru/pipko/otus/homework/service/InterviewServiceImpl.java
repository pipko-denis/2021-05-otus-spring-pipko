package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

public class InterviewServiceImpl implements InterviewService{

    private final QuestionDao questionDao;

    private final AskQuestionsService askQuestionsService;

    private final ValidateQuestionService validateQuestionService;

    private final PrintService printService;

    private final DisplayService displayService;


    public InterviewServiceImpl(QuestionDao questionDao, PrintService printService
            , AskQuestionsService askQuestionsService, ValidateQuestionService validateQuestionService, DisplayService displayService){
        this.questionDao = questionDao;
        this.printService = printService;
        this.askQuestionsService = askQuestionsService;
        this.validateQuestionService = validateQuestionService;
        this.displayService = displayService;
    }

    public void takeAnInterview() {
        String firstName = this.askQuestionsService.askSomething("What is your first name?");
        String lastName = this.askQuestionsService.askSomething("What is your last name?");
        Interview interview = new Interview(firstName,lastName);


        try {
            interview.setQuestionList(questionDao.getQuestions());
        } catch (QuestionsDaoException ex) {
            this.printService.printLn("Sorry "+interview.getUserName()+"! Getting questions error: "+ex.getMessage());
        }

        try {
            this.validateQuestionService.validateQuestionsList(interview.getQuestionList());
        } catch (ValidateQuestionException ex) {
            this.printService.printLn("Sorry "+interview.getUserName()+"! Validating questions error: "+ex.getMessage());
        }

        try {
            this.askQuestionsService.askQuestions(interview.getQuestionList());
            this.displayService.displayInterviewResults(interview);
        } catch (Exception ex) {
            this.printService.printLn("Sorry "+interview.getUserName()+"! Asking questions error: "+ex.getMessage());
        }


    }





}
