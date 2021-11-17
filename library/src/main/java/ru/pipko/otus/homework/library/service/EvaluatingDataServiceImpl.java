package ru.pipko.otus.homework.library.service;

import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.exceptions.EvaluatingException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EvaluatingDataServiceImpl implements EvaluatingDataService{

    private final static Pattern PATTERN_POSITIVE_INT = Pattern.compile("\\d+");

    private boolean isThereAreOnlyDigitsInText(String text) {
        if (text == null) {
            return false;
        }
        return PATTERN_POSITIVE_INT.matcher(text).matches();
    }


    @Override
    public void throwExceptionIfNotOnlyDigitsInText(String text, String exceptionMessage) {
        if ( (text == null)|| (!PATTERN_POSITIVE_INT.matcher(text).matches())) {
            throw new EvaluatingException( (exceptionMessage == null) ? "Incorrect parameter \""+text+"\" it should contain only digits!" : exceptionMessage );
        }
    }

    @Override
    public void isTextNotNullAndNotBlank( String text, String exceptionMessage) {
        if ((text == null) || (text.isBlank()) ) {
            throw new EvaluatingException( (exceptionMessage == null) ? "Text \""+text+"\" is incorrect! It should not be blank." : exceptionMessage);
        }
    }

    @Override
    public void checkArrayOnDigitsThrowException(String nameOfEntityForMessage, String[] ids){
        final List<String> idsNotDigits = Arrays.stream(ids).filter(id -> !isThereAreOnlyDigitsInText(id)).collect(Collectors.toList());

        if (! idsNotDigits.isEmpty()){
            throw new EvaluatingException(nameOfEntityForMessage+" ids is incorrect! It should contains only digits! Wrong ids: "+String.join(",",idsNotDigits));
        }
    }

}
