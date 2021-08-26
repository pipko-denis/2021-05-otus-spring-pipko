package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> getAll();

    Optional<Author> getById(long id);

    int insert(Author book);

    int update(Author book);

    int delete(Author book);

}
