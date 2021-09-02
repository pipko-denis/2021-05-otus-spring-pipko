package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(long id);

    int insert(Genre book);

    int update(Genre book);

    int delete(long id);

}
