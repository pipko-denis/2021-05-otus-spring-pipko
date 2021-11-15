package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.dto.BookComment;
import ru.pipko.otus.homework.library.exceptions.EvaluatingException;
import ru.pipko.otus.homework.library.exceptions.ServiceRuntimeException;

import java.util.List;
import java.util.Optional;

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


    @Transactional
    @Override
    public Book addBook(String bookName, String authorsInline, String genreIdsInline)  {

        evaluatingService.isTextNotNullAndNotBlank(bookName,BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final String[] authorsIds = authorsInline.replaceAll(" ","").split(",");
        final List<Author> authors = authorService.getAuthorsById(authorsIds);

        final String[] genreIds = genreIdsInline.replaceAll(" ","").split(",");
        final List<Genre> genres = genreEditorService.getGenresById(genreIds);

        final Book book = new Book(bookName, authors, genres);

        return bookDao.insert(book);
    }


    @Override
    public Book editBook(String id, String bookName, String authorsInline, String genreIdsInline) {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        evaluatingService.isTextNotNullAndNotBlank(bookName,BOOK_NAME_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);

        final Book book = getBookById(id);

        String[] authorsIds = authorsInline.replaceAll(" ","").split(",");
        final List<Author> authors = authorService.getAuthorsById(authorsIds);

        String[] genreIds = genreIdsInline.replaceAll(" ","").split(",");
        final List<Genre> genres = genreEditorService.getGenresById(genreIds);

        book.setName(bookName);
        book.setAuthors(authors);
        book.setGenres(genres);

        return bookDao.update(book);

    }

    @Override
    public Book getBookById(String id) {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long bookId = Long.parseLong(id);

        Optional<Book> optionalBook = bookDao.getById(bookId);

        if (optionalBook.isEmpty())
            throw new ServiceRuntimeException(THERE_ARE_NO_BOOKS_WITH_ID +id);

        return optionalBook.get();

    }

    @Override
    public List<BookComment> getBookCommentsCnt(String limit) {

        evaluatingService.throwExceptionIfNotOnlyDigitsInText(limit, "Incorrect limit value: "+limit);

        final int limitInt = Integer.parseInt(limit);

        try {
            return bookDao.getBookCommentsCount(limitInt);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new ServiceRuntimeException(ex.getMessage(),ex);
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            return bookDao.getAll();
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new ServiceRuntimeException("Book fetch error",ex);
        }
    }

    @Transactional
    @Override
    public int deleteBookById(String id) {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,BOOK_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long bookId = Long.parseLong(id);
        final int deletedRecCount = bookDao.delete(bookId);

        if (deletedRecCount == 0)
            throw new ServiceRuntimeException(THERE_ARE_NO_BOOKS_WITH_ID+id);

        return deletedRecCount;
    }



}
