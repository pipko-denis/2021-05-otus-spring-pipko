package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "false")
public class BookJdbcDao implements BookDao {

    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public List<Book> getAll() {
        return namedJdbc.getJdbcOperations().query("SELECT " +
                        "B.id, B.name as BOOK_name," +
                        "A.id as author_id, A.name as author_name, " +
                        "G.id as genre_id, G.name as genre_name " +
                        " FROM BOOKS as B " +
                        " INNER JOIN AUTHORS as A ON A.id = B.author_id " +
                        " INNER JOIN GENRES as G ON G.id = B.genre_id " +
                        "  ORDER BY B.name; "
                , new BookRowMapper());
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        Book book = namedJdbc.queryForObject("SELECT " +
                "B.id, B.name as BOOK_name, " +
                "A.id as author_id, A.name as author_name, " +
                "G.id as genre_id, G.name as genre_name " +
                " FROM BOOKS as B " +
                " INNER JOIN AUTHORS as A ON A.id = B.author_id " +
                " INNER JOIN GENRES as G ON G.id = B.genre_id " +
                "  Where B.id = :id; ", params, new BookRowMapper());
        return book;
    }


    @Override
    public int insert(Book book) {
        final KeyHolder kh = new GeneratedKeyHolder();
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("genre_id", book.getGenres().get(0).getId());
        params.addValue("author_id", book.getAuthors().get(0).getId());

        int result = namedJdbc.update("INSERT INTO BOOKS (name,genre_id,author_id) values (:name, :genre_id, :author_id) ", params, kh);
        book.setId(kh.getKey().longValue());

        return result;
    }

    @Override
    public int update(Book book) {
        return namedJdbc.update("UPDATE BOOKS " +
                " SET name = :name, " +
                " genre_id = :genre_id, " +
                " author_id = :author_id " +
                " Where id = :id  ", Map.of(
                "name", book.getName(),
                "genre_id", book.getGenres().get(0).getId(),
                "author_id", book.getAuthors().get(0).getId(),
                "id", book.getId()));
    }

    @Override
    public int delete(long id) {
        final int recordCount = namedJdbc.update("DELETE FROM BOOKS WHERE id = :id", Map.of("id", id));
        return recordCount;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getLong("id"), resultSet.getString("name")
                    , List.of(new Author(resultSet.getLong("author_id"), resultSet.getString("author_name")))
                    , List.of(new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")))
            );
        }
    }


    @Override
    public Integer getBooksCountByAuthorId(long authorId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authorId", authorId);
        final Integer count = namedJdbc.queryForObject("SELECT COUNT (id) FROM BOOKS as B Where B.author_id = :authorId; ", params, Integer.class);
        return count;
    }

}
