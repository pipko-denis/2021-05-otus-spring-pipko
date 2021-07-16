package ru.pipko.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.service.*;

@Configuration
public class ServicesConfig {

    @Bean()
    InterviewService interviewService(QuestionDao questionDao, PrintService printService
            , AskQuestionsService askQuestionsService, ValidateQuestionService validateQuestionService, DisplayService displayService){
        return new InterviewServiceImpl(questionDao,printService, askQuestionsService,validateQuestionService, displayService);

    }

    @Bean
    PrintService printService(){
        return new PrintStreamService(System.out);
    }

    @Bean
    AskQuestionsService displayQuestionsService(PrintService printService,
                                                DisplayService displayService,
                                                ReadUserResponseService readAnswerService){
        return new AskQuestionsServiceImpl(printService, displayService, readAnswerService);
    }

    @Bean
    ValidateQuestionService validateQuestionService(){
        return new ValidateQuestionServiceImpl();
    }

    @Bean
    DisplayService displayAnswersService(PrintService printService, @Value("${min-pass-count}")int minPassCount){
        return new DisplayServiceImpl(printService,minPassCount);
    }

    @Bean
    ValidateUserResponseService validateUserResponseService(){
        return new ValidateUserResponseServiceImpl();
    }

    @Bean
    ReadUserResponseService readAnswerService(PrintStreamService printService, ValidateUserResponseService validateUserResponseService
            , @Value("${ask-questions-max-attempts}") int maxAttempts){
        return new ReadUserResponseServiceImpl(System.in,printService,validateUserResponseService, maxAttempts);
    }

}
