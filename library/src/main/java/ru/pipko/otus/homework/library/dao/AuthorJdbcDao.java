package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorJdbcDao implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Object> params = Map.of("id",id);
        Author result  = jdbc.queryForObject("SELECT ID, NAME FROM AUTHORS WHERE id = :id ORDER BY NAME", params, new AuthorRowMapper());
        return Optional.of(result) ;
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

    private class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),resultSet.getString("name"));
        }
    }
}
