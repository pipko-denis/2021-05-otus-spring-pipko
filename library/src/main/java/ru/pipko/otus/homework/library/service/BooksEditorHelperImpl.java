package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

@Service
@RequiredArgsConstructor
public class BooksEditorHelperImpl implements BooksEditorHelper {


    public static final String BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS = "Book id is incorrect! It should contains only digits!";
    public static final String BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY = "Book name is incorrect! It should not be empty!";
    public static final String THERE_ARE_NO_BOOKS_WITH_ID = "There are no books with id=";

    private final BookDao bookDao;
    private final GenreEditorHelper genreEditorHelper;
    private final AuthorEditorHelper authorHelper;
    private final EvaluatingDataService evaluatingService;


    @Override
    public Book addBook(String bookName, String authorId, String genreId)  {

        if ( ! evaluatingService.isTextNotNullAndNotBlank(bookName) ) throw new RuntimeException(BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final Author author = authorHelper.getAuthorById(authorId);

        final Genre genre = genreEditorHelper.getGenreById(genreId);

        final Book book = new Book(bookName, author, genre);

        bookDao.insert(book);

        return book;
    }


    @Override
    public Book editBook(String id, String bookName, String authorId, String genreId) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) ) throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);
        if ( ! evaluatingService.isTextNotNullAndNotBlank(bookName) ) throw new RuntimeException(BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final Book book = getBookById(id);

        final Author author = authorHelper.getAuthorById(authorId);

        final Genre genre = genreEditorHelper.getGenreById(genreId);

        book.setName(bookName);
        book.setAuthor(author);
        book.setGenre(genre);

        final int updatedRecCount = bookDao.update(book);
        if (updatedRecCount == 0) throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID+id+". Zero records updated");

        return book;

    }

    @Override
    public Book getBookById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) ) throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);
        final Long bookId = Long.valueOf(id);
        try {
            return bookDao.getById(bookId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID +id);
        }
    }

    @Override
    public int deleteBookById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) ) throw new RuntimeException(BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);
        final Long bookId = Long.valueOf(id);
        final int deletedRecCount = bookDao.delete(bookId);
        if (deletedRecCount == 0) throw new RuntimeException(THERE_ARE_NO_BOOKS_WITH_ID+id);
        return deletedRecCount;
    }



}
