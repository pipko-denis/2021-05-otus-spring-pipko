package ru.pipko.otus.homework.library.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
