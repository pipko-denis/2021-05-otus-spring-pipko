package ru.pipko.otus.homework.library.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@NamedEntityGraph(name = "graph-authors", attributeNodes = {@NamedAttributeNode("authors")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(targetEntity = Author.class, cascade = {CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "author_id")})
    @ToString.Exclude
    private List<Author> authors;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "books_genres", joinColumns = { @JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    @ToString.Exclude
    private List<Genre> genres;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(targetEntity = Comment.class,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String name, List<Author> authors, List<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = new ArrayList<>();
    }
}
