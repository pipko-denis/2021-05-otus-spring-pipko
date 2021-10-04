package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;

public interface GenreEditorService {

    Genre getGenreById(String id);

    List<Genre> getGenresById(String[] idsInline);

    Genre addGenre(Genre genre);

    List<Genre> getAll();
}
