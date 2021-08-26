package ru.pipko.otus.homework.library.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Genre {

    private Long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
