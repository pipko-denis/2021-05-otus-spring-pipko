package ru.pipko.otus.homework.domain;

public class Student {

    private final String firstName;

    private final String lastName;


    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
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
