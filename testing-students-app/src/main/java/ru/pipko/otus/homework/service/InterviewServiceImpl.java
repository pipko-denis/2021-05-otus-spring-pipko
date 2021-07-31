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

    private final PrintLocalizedMessagesService printLocalizedMessagesService;

    private final ReadAnswerService readAnswerService;


    public InterviewServiceImpl(QuestionDao questionDao, PrintService printService
            , AskQuestionsService askQuestionsService, ValidateQuestionService validateQuestionService,
                                DisplayService displayService, PrintLocalizedMessagesService printLocalizedMessagesService,
                                ReadAnswerService readAnswerService){
        this.questionDao = questionDao;
        this.printService = printService;
        this.askQuestionsService = askQuestionsService;
        this.validateQuestionService = validateQuestionService;
        this.displayService = displayService;
        this.printLocalizedMessagesService = printLocalizedMessagesService;
        this.readAnswerService = readAnswerService;
    }

    public void takeAnInterview() {

        Student student = findOutStudentName();
        try {

            List<Question> questionList = getQuestionsListFromRepository();

            Interview interview = new Interview(student,questionList);

            validateQuestionsList(questionList);

            askQuestions(questionList);

            displayInterviewResults(interview);

        } catch (QuestionsDaoException ex) {
            printLocalizedMessagesService.printLocalizedMessage("strings.error.getting",ex.getMessage());
        } catch (ValidateQuestionException ex) {
            printLocalizedMessagesService.printLocalizedMessage("strings.error.validating",ex.getMessage());
        }



    }

    private Student findOutStudentName() {
        String firstName = readAnswerService.readAnswerForQuestion("strings.first.name", (String[]) null);
        String lastName = readAnswerService.readAnswerForQuestion("strings.last.name",(String[]) null);
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
