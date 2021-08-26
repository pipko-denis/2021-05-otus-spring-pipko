package ru.pipko.otus.homework.library.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Book {

    private Long id;

    private String name;

    private Author author;

    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
