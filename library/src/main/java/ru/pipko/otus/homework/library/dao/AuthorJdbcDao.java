package ru.pipko.otus.homework.library.dao;

import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJdbcDao implements AuthorDao{
    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.empty();
    }

    @Override
    public int insert(Author book) {
        return 0;
    }

    @Override
    public int update(Author book) {
        return 0;
    }

    @Override
    public int delete(Author book) {
        return 0;
    }
}
