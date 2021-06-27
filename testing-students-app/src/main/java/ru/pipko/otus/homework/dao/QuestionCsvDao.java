package ru.pipko.otus.homework.dao;

import ru.pipko.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionCsvDao implements QuestionDao {

    private final String csvFileName;

    public QuestionCsvDao(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    @Override
    public List<Question> getQuestions() throws IOException {
        List<Question> result = new ArrayList<>();


        InputStream inputStream = QuestionCsvDao.class.getClassLoader().getResourceAsStream(this.csvFileName);
        if (inputStream == null){
            throw  new IOException("Can't read file because input stream is null");
        }
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String line;
        String[] row;
        while ((line = reader.readLine()) != null) {
            row = line.split(";");
            //Skipping questions with no answers
            if (row.length < 2) continue;

            result.add(new Question(row[0], Arrays.copyOfRange(row, 1, row.length )));
        }

        if (reader != null) reader.close();
        if (streamReader != null) streamReader.close();
        if (inputStream != null) inputStream.close();


        return result;
    }



}
