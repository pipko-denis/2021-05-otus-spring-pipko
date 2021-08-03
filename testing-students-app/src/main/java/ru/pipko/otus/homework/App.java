package ru.pipko.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.pipko.otus.homework.service.*;

@SpringBootApplication
@EnableAspectJAutoProxy
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        InterviewService interviewService = context.getBean("interviewService", InterviewService.class);

        interviewService.takeAnInterview();

    }

}
