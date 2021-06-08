package ru.pipko.otus.homework.dao;

import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.service.QuestionExtractorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoSimple implements IQuestionDao{

    private final List<Question> questions;

    @Override
    public Optional<Question> getNextQuestion() {
        return questions.stream().filter(question -> ! question.isPassed() ).findFirst();
    }

    @Override
    public int getQuestionsCount() {
            return (this.questions == null) ? 0 : this.questions.size();
    }


    public QuestionDaoSimple(QuestionExtractorService questionsExtractor) {
        if (questionsExtractor.getQuestions() == null){
            this.questions = new ArrayList<>();
        }else{
            this.questions = questionsExtractor.getQuestions();
        }
    }

}
