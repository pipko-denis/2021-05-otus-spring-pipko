package ru.pipko.otus.homework.library.service;

import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.dto.BookComment;

import java.util.List;

public interface BooksEditorService {

    Book addBook(String bookName, String authorsInline, String genreIdsInline);

    Book editBook(String bookId, String bookName, String authorsInline, String genreIdsInline) ;

    Book getBookById(String id);

    @Transactional
    List<BookComment> getBookCommentsCnt(String limit);

    List<Book> getAllBooks();

    int deleteBookById(String id);

}
