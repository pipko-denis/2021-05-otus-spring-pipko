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

@Repository
@RequiredArgsConstructor
public class AuthorJdbcDao implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> getAll() {
        return jdbc.getJdbcOperations().query("SELECT ID, NAME FROM AUTHORS ORDER BY NAME",new AuthorRowMapper());
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Map.of("id",id);
        Author result  = jdbc.queryForObject("SELECT ID, NAME FROM AUTHORS WHERE id = :id ORDER BY NAME", params, new AuthorRowMapper());
        return result;
    }

    @Override
    public List<Author> getByName(String userInput) {
        Map<String, Object> params = Map.of("name",userInput);
        List<Author> result = jdbc.query("SELECT ID, NAME FROM AUTHORS WHERE name = :name ORDER BY NAME", params, new AuthorRowMapper());
        return result ;
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
        throw new RuntimeException("Method update is not implemented");
    }

    @Override
    public int delete(long id) {
        return jdbc.update("DELETE FROM AUTHORS WHERE id = :id",Map.of("id",id));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),resultSet.getString("name"));
        }
    }
}
