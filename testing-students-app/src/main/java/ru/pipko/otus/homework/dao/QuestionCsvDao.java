package ru.pipko.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionCsvDao implements QuestionDao {

    private final String csvFileName;

    public QuestionCsvDao(@Value("${csv-file-name}") String csvFileName) {
        this.csvFileName = csvFileName;
    }

    @Override
    public List<Question> getQuestions() throws QuestionsDaoException {
        List<Question> result = new ArrayList<>();


        try (
                InputStream inputStream = QuestionCsvDao.class.getClassLoader().getResourceAsStream(this.csvFileName);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
        ){
            String line;
            String[] row;
            while ((line = reader.readLine()) != null) {
                row = line.split(";");

                if (row.length < 2) {
                    throw new QuestionsDaoException("The row \""+line+"\" row doesn't contain enough blocks separated by \";\"");
                }

                result.add(new Question(row[0], Arrays.copyOfRange(row, 1, row.length)));
            }
        } catch (Exception ex){
            throw new QuestionsDaoException("",ex.getCause()) ;
        }

        return result;
    }



}
