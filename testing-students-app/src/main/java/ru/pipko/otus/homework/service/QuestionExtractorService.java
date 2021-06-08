package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionExtractorService implements IQuestionExtractorService {

    private final List<Question> questions;

    @Override
    public List<Question> getQuestions() {
        return this.questions;
    }


    public QuestionExtractorService(String csvFileName) {
        questions = new ArrayList<>();
        try (InputStream inputStream = QuestionExtractorService.class.getClassLoader().getResourceAsStream(csvFileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader);
             ) {

            String line;
            String[] row;
            while ((line = reader.readLine()) != null) {
                row = line.split(";");
                //Skipping questions with no answers
                if (row.length < 2) continue;

                this.questions.add(new Question(row[0], Arrays.copyOfRange(row, 1, row.length )));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
