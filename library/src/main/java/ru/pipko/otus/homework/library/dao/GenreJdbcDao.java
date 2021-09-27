package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "false")
public class GenreJdbcDao implements GenreDao {

    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public List<Genre> getAll() {
        return namedJdbc.getJdbcOperations().query("SELECT ID, NAME FROM GENRES ORDER BY NAME", new GenreRowMapper());
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        Genre result = namedJdbc.queryForObject("SELECT ID, NAME FROM GENRES WHERE id = :id ORDER BY NAME", params, new GenreRowMapper());
        return result;
    }

    @Override
    public Genre insert(Genre genre) {
        final KeyHolder kh = new GeneratedKeyHolder();
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        namedJdbc.update("INSERT INTO GENRES (name) values (:name) ", params, kh);
        genre.setId(kh.getKey().longValue());

        return genre;
    }


    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
