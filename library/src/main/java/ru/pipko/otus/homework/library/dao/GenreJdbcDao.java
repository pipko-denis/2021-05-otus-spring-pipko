package ru.pipko.otus.homework.library.dao;

import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreJdbcDao implements GenreDao{

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.empty();
    }

    @Override
    public int insert(Genre book) {
        return 0;
    }

    @Override
    public int update(Genre book) {
        return 0;
    }

    @Override
    public int delete(Genre book) {
        return 0;
    }
}
