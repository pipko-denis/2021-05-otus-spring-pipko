package ru.pipko.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.domain.Student;
import ru.pipko.otus.homework.events.PublisherForEvents;
import ru.pipko.otus.homework.service.DisplayService;
import ru.pipko.otus.homework.service.InterviewService;
import ru.pipko.otus.homework.service.LocalizationServiceImpl;
import ru.pipko.otus.homework.service.ReadAnswerService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class ApplicationEventsCommands {

    private final PublisherForEvents publisherForEvents;

    private final InterviewService interviewService;

    private final LocalizationServiceImpl localizationService;

    private final DisplayService displayService;

    private final ReadAnswerService readAnswerService;

    private List<Question> questionsList;

    private Student student;

    @ShellMethod(key = {"login","log","l"}, value = "Login")
    public void login(){
        questionsList = null;
        student = findOutStudentName();
    }

    private Student findOutStudentName() {
        String firstName = readAnswerService.readAnswer("strings.first.name", (String[]) null);
        String lastName = readAnswerService.readAnswer("strings.last.name",(String[]) null);
        Student student = new Student(firstName, lastName);
        publisherForEvents.publishUserAssigned(student);
        return student;
    }

    @ShellMethod(key = {"start","s","test"}, value = "Start testing")
    @ShellMethodAvailability(value = "isTestingAvailable")
    public void passTest(){
        questionsList = interviewService.takeAnInterview();
    }

    @ShellMethod(key = {"results","res","r"}, value = "Test results")
    @ShellMethodAvailability(value = "isResultsAvailable")
    public void displayResults(){
        displayService.displayInterviewResults(student,questionsList);
    }

    private Availability isTestingAvailable(){
        if ( student == null) {
            String message = localizationService.localizeMessage("strings.shell.login");
            return Availability.unavailable(message);
        } else{
            return Availability.available();
        }
    }

    @ShellMethod(key = {"h"}, value = "Hello")
    public String sayHello(@ShellOption String name){
        return "Hello "+name;
    }

    @ShellMethod(value = "Hi")
    public String sayHi(@ShellOption String name){
        return "Hi "+name;
    }

    private Availability isResultsAvailable(){
        if ( questionsList == null) {
            String message = localizationService.localizeMessage("strings.shell.results");
            return Availability.unavailable(message);
        } else{
            return Availability.available();
        }
    }



}
