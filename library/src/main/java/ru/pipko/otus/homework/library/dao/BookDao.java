package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(long id);

    /**
     * After execution of the insertion, method "getId()" will return id of the inserted book
     * @param book
     * @return - inserted record count
     */
    int insert(Book book);

    /**
     * @param book
     * @return updated record count
     */
    int update(Book book);

    /**
     * @param id key value from the database
     * @return deleted record count
     */
    int delete(long id);

    Integer getBooksCountByAuthorId(long authorId);
}
