package ru.pipko.otus.homework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.LocalizationService;
import ru.pipko.otus.homework.service.PrintLocalizedMessagesService;
import ru.pipko.otus.homework.service.PrintService;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Aspect
public class InterviewDurationAspect {

    private final PrintLocalizedMessagesService printLocalizedMessagesService;
    private final PrintService printService;

    public InterviewDurationAspect(PrintLocalizedMessagesService printLocalizedMessagesService, PrintService printService){

        this.printLocalizedMessagesService = printLocalizedMessagesService;
        this.printService = printService;
    }


    @Around("execution(* ru.pipko.otus.homework.service.AskQuestionsService.askQuestions(..))")
    public void timingInterviewAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime dtStart = LocalDateTime.now();
        joinPoint.proceed();
        Duration duration = Duration.between(dtStart, LocalDateTime.now());
        printLocalizedMessagesService.printLocalizedMessage("strings.passing.duration",String.valueOf(duration.getSeconds()));
    }

}
