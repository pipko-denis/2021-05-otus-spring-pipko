package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> getAll();

    Optional<Author> getById(long id);

    List<Author> getById(List<Long> ids);

    List<Author> findByName(String name);

    Author insert(Author author);

    int delete(long id);

}
