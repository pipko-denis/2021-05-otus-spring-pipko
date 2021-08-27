package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookJdbcDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> getAll() {
        return jdbc.getJdbcOperations().query( "SELECT " +
                        "B.ID, B.NAME as BOOK_NAME," +
                        "A.ID as AUTHOR_ID, A.NAME as AUTHOR_NAME, " +
                        "G.ID as GENRE_ID, G.NAME as GENRE_NAME " +
                        " FROM BOOKS as B " +
                        " INNER JOIN AUTHORS as A ON A.ID = B.AUTHOR_ID " +
                        " INNER JOIN GENRES as G ON G.ID = B.GENRE_ID " +
                        "  ORDER BY B.NAME; "
                , new BookRowMapper());
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.empty();
    }

    @Override
    public int insert(Book book) {
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getLong("id"), resultSet.getString("name")
                    ,new Author(resultSet.getLong("id"),resultSet.getString("name"))
                    ,new Genre(resultSet.getLong("id"),resultSet.getString("name"))
            );
        }
    }
}
