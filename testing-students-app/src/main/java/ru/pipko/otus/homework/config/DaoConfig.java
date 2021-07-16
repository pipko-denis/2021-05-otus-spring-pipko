package ru.pipko.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.dao.QuestionDao;

@Configuration
public class DaoConfig {

    @Bean
    QuestionDao questionDao(@Value("${csv-file-name}") String csvFileName){
        return new QuestionCsvDao(csvFileName);
    }

}
