package ru.pipko.otus.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.service.*;


@Configuration
@PropertySource("application.properties")
public class AppConfig {

    @Bean()
    InterviewService interviewService(QuestionDao questionDao, PrintService printService
            , DisplayQuestionsService displayQuestionsService, ValidateQuestionService validateQuestionService){
        return new InterviewServiceImpl(questionDao,printService,displayQuestionsService,validateQuestionService);

    }

    @Bean
    QuestionDao questionDao(@Value("${csv.file.name}") String csvFileName){
        return new QuestionCsvDao(csvFileName);
    }

    @Bean
    PrintService printService(){
        return new PrintStreamService(System.out);
    }

    @Bean
    DisplayQuestionsService displayQuestionsService(PrintService printService,
                                                    DisplayAnswersService displayAnswersService,
                                                    ReadAnswerService readAnswerService,
                                                    ValidateUserResponseService validateUserResponseService){
        return new DisplayQuestionsServiceImpl(printService,displayAnswersService, readAnswerService, validateUserResponseService);
    }

    @Bean
    ValidateQuestionService validateQuestionService(){
        return new ValidateQuestionServiceImpl();
    }

    @Bean
    DisplayAnswersService displayAnswersService(PrintService printService){
        return new DisplayAnswersServiceImpl(printService);
    }

    @Bean
    ValidateUserResponseService validateUserResponseService(){
        return new ValidateUserResponseServiceImpl();
    }

    @Bean
    ReadAnswerService readAnswerService(PrintStreamService printService,ValidateUserResponseService validateUserResponseService
            ,@Value("${ask.questions.max.attempts}") int maxAttempts){
        return new ReadAnswerServiceImpl(System.in,printService,validateUserResponseService, maxAttempts);
    }
}
