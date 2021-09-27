package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksEditorServiceImpl implements BooksEditorService {


    public static final String BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS = "Book id is incorrect! It should contains only digits!";
    public static final String BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY = "Book name is incorrect! It should not be empty!";
    public static final String THERE_ARE_NO_BOOKS_WITH_ID = "There are no books with id=";

    private final BookDao bookDao;
    private final GenreEditorService genreEditorService;
    private final AuthorEditorService authorService;
    private final EvaluatingDataService evaluatingService;


    @Override
    public Book addBook(String bookName, String authorId, String genreId)  {

        if ( ! evaluatingService.isTextNotNullAndNotBlank(bookName) )
            throw new RuntimeException(BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final Author author = authorService.getAuthorById(authorId);

        final Genre genre = genreEditorService.getGenreById(genreId);

        final Book book = new Book(bookName, List.of(author), List.of(genre));

        bookDao.insert(book);

        return book;
    }


    @Override
    public Book editBook(String id, String bookName, String authorId, String genreId) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);
        if ( ! evaluatingService.isTextNotNullAndNotBlank(bookName) )
            throw new RuntimeException(BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final Book book = getBookById(id);

        final Author author = authorService.getAuthorById(authorId);

        final Genre genre = genreEditorService.getGenreById(genreId);

        book.setName(bookName);
        book.setAuthors(List.of(author));
        book.setGenres(List.of(genre));

        final int updatedRecCount = bookDao.update(book);
        if (updatedRecCount == 0)
            throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID+id+". Zero records updated");

        return book;

    }

    @Override
    public Book getBookById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long bookId = Long.parseLong(id);

        try {
            return bookDao.getById(bookId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID +id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return bookDao.getAll();
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("Book fetch error");
        }
    }

    @Override
    public int deleteBookById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long bookId = Long.parseLong(id);
        final int deletedRecCount = bookDao.delete(bookId);

        if (deletedRecCount == 0)
            throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID+id);

        return deletedRecCount;
    }



}
