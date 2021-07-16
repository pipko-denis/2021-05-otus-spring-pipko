package ru.pipko.otus.homework;

import org.springframework.context.annotation.*;
import ru.pipko.otus.homework.service.*;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        InterviewService interviewService = context.getBean("interviewService", InterviewService.class);

        interviewService.takeAnInterview();

        context.close();
    }

}
