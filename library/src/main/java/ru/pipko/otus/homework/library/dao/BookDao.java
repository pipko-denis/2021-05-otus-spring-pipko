package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.dto.BookComment;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(long id);

    /**
     * After execution of the insertion, method "getId()" will return id of the inserted book
     * @param book - book entity
     * @return - inserted record count
     */
    Book insert(Book book);

    /**
     * @param book - book entity
     * @return updated record count
     */
    Book update(Book book);

    /**
     * @param id key value from the database
     * @return deleted record count
     */
    int delete(long id);

    long getBooksCountByAuthorId(long authorId);

    List<BookComment> getBookCommentsCount(int limit);
}
