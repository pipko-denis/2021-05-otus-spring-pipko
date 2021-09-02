package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreJdbcDao implements GenreDao {

    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public List<Genre> getAll() {
        return namedJdbc.getJdbcOperations().query("SELECT ID, NAME FROM GENRES ORDER BY NAME", new GenreRowMapper());
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        Genre result = namedJdbc.queryForObject("SELECT ID, NAME FROM GENRES WHERE id = :id ORDER BY NAME", params, new GenreRowMapper());
        return result;
    }

    @Override
    public int insert(Genre genre) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        int insertedRecordCount = namedJdbc.update("INSERT INTO GENRES (name) values (:name) ", params, kh);
        genre.setId(kh.getKey().longValue());

        return insertedRecordCount;
    }

    @Override
    public int update(Genre book) {
        throw new RuntimeException("Method update is not implemented");
    }

    @Override
    public int delete(long id) {
        throw new RuntimeException("Method delete is not implemented");
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
