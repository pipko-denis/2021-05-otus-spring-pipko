package ru.pipko.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.domain.Question;

import java.util.regex.Pattern;

@Service
public class ValidateUserResponseServiceImpl implements ValidateUserResponseService {


    @Override
    public boolean isUserResponseIsValid(Question question, String response) {
        final Pattern pattern = Pattern.compile("[1-"+question.getAnswers().size()+"]");
        if ( pattern.matcher(response).find()){
            int responseIntValue =  Integer.parseInt(response);
            return  (responseIntValue > 0) && (responseIntValue <= question.getAnswers().size() ) ;
        } else {
            return false;
        }
    }
}
