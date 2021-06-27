package ru.pipko.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pipko.otus.homework.service.InterviewService;


public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        InterviewService interviewService = context.getBean("interviewService", InterviewService.class);

        interviewService.takeAnInterview();

        context.close();
    }
}
