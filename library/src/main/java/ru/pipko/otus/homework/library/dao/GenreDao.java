package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    List<Genre> getAll();

    Optional<Genre> getById(long id);

    List<Genre> getById(List<Long> ids);

    Genre insert(Genre book);

}
