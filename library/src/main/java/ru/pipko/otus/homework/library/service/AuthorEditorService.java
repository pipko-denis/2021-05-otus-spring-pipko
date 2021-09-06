package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;

public interface AuthorEditorService {

    Author getAuthorById(String description);

    int deleteAuthorById(String id);

    int insert(Author author);

    List<Author> getAll();
}
