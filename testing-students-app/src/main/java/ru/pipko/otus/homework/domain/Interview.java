package ru.pipko.otus.homework.domain;

import java.util.List;

public class Interview {

    private String firstName;

    private String lastName;

    List<Question> questionList;

    public Interview(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getUserName() {
        boolean firstNameIsValid = (this.firstName != null) && (!this.firstName.isBlank());
        boolean lastNameIsValid = (this.lastName != null) && (!this.lastName.isBlank());

        if (firstNameIsValid && lastNameIsValid) {
            return this.firstName + " " + this.lastName;
        } else {
            if (!firstNameIsValid && !lastNameIsValid) {
                return "anonymous";
            } else {
                if (firstNameIsValid) return firstName;
                else return lastName;
            }
        }
    }

}
