package ru.pipko.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.pipko.otus.homework.service.InterviewService;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        InterviewService interviewService = context.getBean("  interviewService", InterviewService.class);

        interviewService.takeAnInterview();

        context.close();
    }
}
