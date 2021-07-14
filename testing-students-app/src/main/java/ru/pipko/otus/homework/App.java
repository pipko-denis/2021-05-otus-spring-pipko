package ru.pipko.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pipko.otus.homework.service.InterviewService;


public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        InterviewService interviewService = context.getBean("interviewService", InterviewService.class);

        interviewService.takeAnInterview();

        context.close();
    }
}
