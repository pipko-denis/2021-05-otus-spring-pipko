package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Interview;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.domain.Student;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

@Service("interviewService")
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

        List<Question> questionList;
        Interview interview;
        try {
            Student student = findOutStudentName();

            questionList = getQuestionsListFromRepository();

            interview = new Interview(student,questionList);

            validateQuestionsList(questionList);

        } catch (QuestionsDaoException ex) {
            printService.printLn("Getting questions error: "+ex.getMessage());
            return;
        } catch (ValidateQuestionException ex) {
            printService.printLn("Validating questions error: "+ex.getMessage());
            return;
        }

        askQuestions(questionList);

        displayInterviewResults(interview);

    }

    private Student findOutStudentName() {
        String firstName = this.askQuestionsService.askSomething("What is your first name?");
        String lastName = this.askQuestionsService.askSomething("What is your last name?");
        return new Student(firstName, lastName);
    }

    private List<Question> getQuestionsListFromRepository() throws QuestionsDaoException {
        return questionDao.getQuestions();
    }

    private void validateQuestionsList(List<Question> questionList) throws ValidateQuestionException {
        validateQuestionService.validateQuestionsList(questionList);
    }

    private void askQuestions(List<Question> questionList) {
        askQuestionsService.askQuestions(questionList);
    }

    private void displayInterviewResults( Interview interview) {
        displayService.displayInterviewResults(interview);
    }









}
