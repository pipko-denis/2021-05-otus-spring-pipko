package ru.pipko.otus.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.pipko.otus.homework.service.*;


//@Configuration
//@PropertySource("application.properties")
public class AppConfig {


/*    @Bean
    PrintService printService(){
        return new PrintStreamService(System.out);
    }


    @Bean
    ReadUserResponseService readAnswerService(PrintStreamService printService, ValidateUserResponseService validateUserResponseService
            , @Value("${ask-questions-max-attempts}") int maxAttempts){
        return new ReadUserResponseServiceImpl(System.in,printService,validateUserResponseService, maxAttempts);
    }*/
}
