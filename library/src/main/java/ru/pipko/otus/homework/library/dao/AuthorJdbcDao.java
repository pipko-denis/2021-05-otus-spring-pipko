package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        return jdbc.getJdbcOperations().query("SELECT ID, NAME FROM AUTHORS ORDER BY NAME",new AuthorRowMapper());
    }

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Object> params = Map.of("id",id);
        Author result  = jdbc.queryForObject("SELECT ID, NAME FROM AUTHORS WHERE id = :id ORDER BY NAME", params, new AuthorRowMapper());
        return Optional.of(result) ;
    }

    @Override
    public int insert(Author book) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",book.getName());
        int recCount = jdbc.update("INSERT INTO AUTHORS (NAME) VALUES(:name);",params,kh);
        book.setId(kh.getKey().longValue());
        return recCount;
    }

    @Override
    public int update(Author book) {
        return 0;
    }

    @Override
    public int delete(Author book) {
        return 0;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),resultSet.getString("name"));
        }
    }
}
