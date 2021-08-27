package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> getAll();

    Optional<Book> getById(long id);

    int insert(Book book);

    int update(Book book);

    int delete(long id);

}
