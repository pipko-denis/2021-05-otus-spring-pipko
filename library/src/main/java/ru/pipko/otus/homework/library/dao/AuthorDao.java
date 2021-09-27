package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(long id);

    List<Author> getById(List<Long> ids);

    List<Author> getByName(String name);

    Author insert(Author author);

    int delete(long id);

}
