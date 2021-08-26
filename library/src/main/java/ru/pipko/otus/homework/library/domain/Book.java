package ru.pipko.otus.homework.library.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    private Long id;

    private String name;

    private Author author;

    private Genre genre;

}
