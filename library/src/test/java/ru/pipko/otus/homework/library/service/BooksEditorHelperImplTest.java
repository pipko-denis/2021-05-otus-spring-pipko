package ru.pipko.otus.homework.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BooksEditorHelperImpl должен")
@SpringBootTest(classes = {BooksEditorHelperImpl.class})
class BooksEditorHelperImplTest {

    public static final int EXPECTED_DELETED_RECORD_COUNT = 1;
    public static final String EXPECTED_BOOK_NAME = "ExpectedBookName";
    @Autowired
    private BooksEditorHelper booksEditorHelper;

    @MockBean
    private AuthorDao authorJdbcDao;

    @MockBean
    private BookDao bookJdbcDao;

    @MockBean
    private GenreDao genreJdbcDao;

    @MockBean
    private GenreEditorHelper genreEditorHelper;

    @MockBean
    private AuthorEditorHelper authorEditorHelper;

    @MockBean
    private EvaluatingDataService evaluatingDataService;

    @Configuration
    static class Config {

    }

    @Test
    @Description("корретно добавялть книгу")
    void addBook() {
        final Author expectedAuthor = new Author(1L, "Author1");
        final Genre expectedGenre = new Genre(1L, "Genre1");

        Mockito.when(authorEditorHelper.getAuthorById("1")).thenReturn(expectedAuthor);
        Mockito.when(genreEditorHelper.getGenreById("1")).thenReturn(expectedGenre);
        Mockito.when(bookJdbcDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);

        final Book actualBook = booksEditorHelper.addBook(EXPECTED_BOOK_NAME, "1", "1");

        assertThat(actualBook.getAuthor()).as("Все поля автора %s должны совпадать",expectedAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
        assertThat(actualBook.getGenre()).usingRecursiveComparison().isEqualTo(expectedGenre);
        assertThat(actualBook.getName()).isEqualTo(EXPECTED_BOOK_NAME);

    }

    @Test
    @Description("корректно изменять данные книги")
    void editBook() {
        final Author author1 = new Author(1L, "Author1");
        final Genre genre1 = new Genre(1L, "Genre1");
        final Author author2 = new Author(2L, "Author2");
        final Genre genre2 = new Genre(2L, "Genre2");
        final Book bookForUpdateCheck = new Book(1L, "Book1", author1, genre1);
        final Book bookExpected = new Book(1L, "New name", author2, genre2);

        Mockito.when(bookJdbcDao.getById(1)).thenReturn(bookForUpdateCheck);
        Mockito.when(authorEditorHelper.getAuthorById("2")).thenReturn(author2);
        Mockito.when(genreEditorHelper.getGenreById("2")).thenReturn(genre2);
        Mockito.when(bookJdbcDao.update(bookForUpdateCheck)).thenReturn(1);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);
        Mockito.when(evaluatingDataService.isThereAreOnlyDigitsInText(Mockito.any())).thenReturn(true);

        Book bookAfterEditing = booksEditorHelper.editBook("1","New name","2","2");

        assertThat(bookAfterEditing).usingRecursiveComparison().isEqualTo(bookExpected);
    }

    @Test
    @Description("корректно выдавать книгу по идентификатору")
    void getBookById() {

        final Author author1 = new Author(1L, "Author1");
        final Genre genre1 = new Genre(1L, "Genre1");
        final Book bookExpected = new Book(1L, "Book1", author1, genre1);

        Mockito.when(bookJdbcDao.getById(1)).thenReturn(bookExpected);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);
        Mockito.when(evaluatingDataService.isThereAreOnlyDigitsInText(Mockito.any())).thenReturn(true);

        Book bookFromHelper = booksEditorHelper.getBookById("1");

        assertThat(bookFromHelper).usingRecursiveComparison().isEqualTo(bookExpected);

    }

    @Test
    @Description("должен корректно удалять книгу и при запросе книги по идентификатору удалённой книги вызывать исключение")
    void deleteBookByIdAndThrowRuntimeExceptionOnGetByThisId() {

        Mockito.when(bookJdbcDao.delete(1)).thenReturn(1);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);
        Mockito.when(evaluatingDataService.isThereAreOnlyDigitsInText(Mockito.any())).thenReturn(true);
        Mockito.when(bookJdbcDao.getById(1)).thenThrow(EmptyResultDataAccessException.class);

        int deletedRecCount = booksEditorHelper.deleteBookById("1");

        assertThat(deletedRecCount).usingRecursiveComparison().isEqualTo(EXPECTED_DELETED_RECORD_COUNT);

        assertThatCode( () -> booksEditorHelper.getBookById("1")).isInstanceOf(RuntimeException.class);

    }
}