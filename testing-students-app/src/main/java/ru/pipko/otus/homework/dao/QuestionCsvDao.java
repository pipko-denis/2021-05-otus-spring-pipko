package ru.pipko.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.config.CsvCustomProperties;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
//@ConfigurationProperties(prefix = "application")
public class QuestionCsvDao implements QuestionDao {

    private final CsvCustomProperties csvCustomProperties;

    public QuestionCsvDao( CsvCustomProperties csvCustomProperties) {
                           //@Value("#{application.localized-files}") Map<String,String> localizedFiles) {
                           //@Value("#{ (T(java.util.HashMap)) ${application.localized-files})}") Map<String,String> localizedFiles) {
        this.csvCustomProperties = csvCustomProperties;
        //System.out.println(localizedFiles);
    }

    @Override
    public List<Question> getQuestions() throws QuestionsDaoException {
        List<Question> result = new ArrayList<>();

        String fileName = csvCustomProperties.getCsvFileName();

        if ((fileName == null) || (fileName.isBlank())) throw new QuestionsDaoException("В найстройках не указан путь к файлу csv") ;

        try (
                InputStream inputStream = QuestionCsvDao.class.getClassLoader().getResourceAsStream(fileName);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
        ) {
            String line;
            String[] row;
            while ((line = reader.readLine()) != null) {
                row = line.split(";");
                result.add(new Question(row[0], Arrays.copyOfRange(row, 1, row.length)));
            }
        } catch (Exception ex){
            throw new QuestionsDaoException("Во время получения вопросов возникла ошибка: "+ex.getMessage(),ex) ;
        }

        return result;
    }



}
