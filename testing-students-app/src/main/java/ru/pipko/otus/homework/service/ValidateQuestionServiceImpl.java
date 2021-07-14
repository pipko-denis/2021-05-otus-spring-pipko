package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

public class ValidateQuestionServiceImpl implements ValidateQuestionService{

    @Override
    public void validateQuestionsList(List<Question> questions) throws ValidateQuestionException {

        if (questions ==null){
            throw  new ValidateQuestionException("Questions list is null");
        }

        if (questions.isEmpty()){
            throw  new ValidateQuestionException("Questions list is empty");
        }

        Question question;
        for (int i = 0; i < questions.size(); i++) {
            question = questions.get(i);

            if (question ==null){
                throw  new ValidateQuestionException("Question #"+i+" is null");
            }

            if (question.getText() ==null){
                throw  new ValidateQuestionException("Question #"+i+" text is null");
            }

            if (question.getText().isBlank() ){
                throw  new ValidateQuestionException("Question #"+i+" text is blank");
            }

            if (question.getAnswers() ==null){
                throw  new ValidateQuestionException("Question #"+i+" answers list is null");
            }

            if (question.getAnswers().isEmpty()){
                throw  new ValidateQuestionException("Question #"+i+" answers list is empty");
            }

            //Checking answers
            List<Answer> answers = question.getAnswers();
            Answer answer;
            for (int j = 0; j < answers.size(); j++) {
                answer = answers.get(j);

                if (answer ==null){
                    throw  new ValidateQuestionException("Question #"+i+", answer #"+j+" is null");
                }

                if (answer.getText() ==null){
                    throw  new ValidateQuestionException("Question #"+i+", answer #"+j+" text is null");
                }

                if (answer.getText().isBlank() ){
                    throw  new ValidateQuestionException("Question #"+i+", answer #"+j+" text is blank");
                }

            }




        }

    }
}
