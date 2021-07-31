package ru.pipko.otus.homework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.pipko.otus.homework.service.LocalizationService;
import ru.pipko.otus.homework.service.PrintService;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Aspect
public class InterviewDurationAspect {

    private final LocalizationService localizationService;
    private final PrintService printService;

    public InterviewDurationAspect(LocalizationService localizationService, PrintService printService){

        this.localizationService = localizationService;
        this.printService = printService;
    }


    @Around("execution(* ru.pipko.otus.homework.service.AskQuestionsService.askQuestions(..))")
    public void timingInterviewAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime dtStart = LocalDateTime.now();
        joinPoint.proceed();
        Duration duration = Duration.between(dtStart, LocalDateTime.now());
        String message = localizationService.localizeMessage("strings.passing.duration",String.valueOf(duration.getSeconds()));
        printService.printLn(message);
    }

}
