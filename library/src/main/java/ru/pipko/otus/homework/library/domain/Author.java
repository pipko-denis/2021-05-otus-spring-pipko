package ru.pipko.otus.homework.library.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Author {

    private Long id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
