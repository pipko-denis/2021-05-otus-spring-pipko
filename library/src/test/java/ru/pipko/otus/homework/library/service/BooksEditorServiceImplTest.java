package ru.pipko.otus.homework.library.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.exceptions.EvaluatingException;
import ru.pipko.otus.homework.library.exceptions.ServiceRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Сервис BooksEditorServiceImpl должен")
@SpringBootTest
@Import({BooksEditorServiceImpl.class, EvaluatingDataServiceImpl.class})
class BooksEditorServiceImplTest {

    public static final int EXPECTED_DELETED_RECORD_COUNT = 1;
    public static final String EXPECTED_BOOK_NAME = "ExpectedBookName";
    public static final String AUTHOR_1 = "Author1";
    public static final String GENRE_1 = "Genre1";
    public static final String COMMENT_1 = "Comment1";
    public static final String TEXT_TO_PRODUCE_ERROR = "text to produce error";
    public static final String NOT_BLANK_BOOK_NAME = "Not blank book name";
    public static final String BLANK_TEXT_TO_PRODUCE_ERROR = "";


    @Autowired
    private BooksEditorService booksEditorService;

    @Autowired
    private EvaluatingDataService evaluatingDataService;

    @MockBean
    private AuthorDao authorJdbcDao;

    @MockBean
    private BookDao bookJpaDao;

    @MockBean
    private GenreDao genreJdbcDao;

    @MockBean
    private GenreEditorService genreEditorService;

    @MockBean
    private AuthorEditorService authorEditorService;

    @Configuration
    static class Config {

    }

    @Test
    @Description("корретно добавлять книгу")
    void addBook() {
        final Author expectedAuthor = new Author(1L, AUTHOR_1);
        final Genre expectedGenre = new Genre(1L, GENRE_1);
        final Book expectedBook = new Book(EXPECTED_BOOK_NAME, Lists.list(expectedAuthor), Lists.list(expectedGenre));

        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(Lists.list(expectedAuthor));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(Lists.list(expectedGenre));
        Mockito.when(bookJpaDao.insert(Mockito.any())).thenReturn(expectedBook);

        final Book actualBook = booksEditorService.addBook(EXPECTED_BOOK_NAME, "1", "1");

        assertThat(actualBook.getAuthors()).as("Все поля автора %s должны совпадать",expectedAuthor).usingRecursiveComparison().isEqualTo(Lists.list(expectedAuthor));
        assertThat(actualBook.getGenres()).usingRecursiveComparison().isEqualTo(Lists.list(expectedGenre));
        assertThat(actualBook.getName()).isEqualTo(expectedBook.getName());

    }

    @Test
    @Description("не позволять сохранять не корректные данные")
    void editBook() {
/**/
        final Author author1 = new Author(1L, AUTHOR_1);
        final Genre genre1 = new Genre(1L, GENRE_1);
        final Author author2 = new Author(2L, "Author2");
        final Genre genre2 = new Genre(2L, "Genre2");
        final Book bookForUpdateCheck = new Book(1L, "Book1", List.of(author1) , List.of(genre1), Collections.emptyList());
        final Book bookExpected = new Book(1L, "New name", List.of(author2), List.of(genre2), Collections.emptyList());

        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(bookForUpdateCheck));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(author2));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(genre2));

        assertThatCode( () -> booksEditorService.editBook(TEXT_TO_PRODUCE_ERROR,NOT_BLANK_BOOK_NAME,"2","2")).isInstanceOf(EvaluatingException.class);

        assertThatCode( () -> booksEditorService.editBook(BLANK_TEXT_TO_PRODUCE_ERROR, NOT_BLANK_BOOK_NAME,"2","2")).isInstanceOf(EvaluatingException.class);

        assertThatCode( () -> booksEditorService.editBook("1", BLANK_TEXT_TO_PRODUCE_ERROR,"2","2")).isInstanceOf(EvaluatingException.class);

        //booksEditorService.editBook("1",NOT_BLANK_BOOK_NAME, TEXT_TO_PRODUCE_ERROR,"2");

        assertThatCode( () -> booksEditorService.editBook("1",NOT_BLANK_BOOK_NAME, TEXT_TO_PRODUCE_ERROR,"2")).isInstanceOf(EvaluatingException.class);

        assertThatCode( () -> booksEditorService.editBook("1",NOT_BLANK_BOOK_NAME, "2",TEXT_TO_PRODUCE_ERROR)).isInstanceOf(EvaluatingException.class);

        /*final Author author1 = new Author(1L, AUTHOR_1);
        final Genre genre1 = new Genre(1L, GENRE_1);
        final Author author2 = new Author(2L, "Author2");
        final Genre genre2 = new Genre(2L, "Genre2");
        final Book bookForUpdateCheck = new Book(1L, "Book1", List.of(author1) , List.of(genre1), Collections.emptyList());
        final Book bookExpected = new Book(1L, "New name", List.of(author2), List.of(genre2), Collections.emptyList());

        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(bookForUpdateCheck));
        Mockito.when(authorEditorService.getAuthorById("2")).thenReturn(author2);
        Mockito.when(genreEditorService.getGenreById("2")).thenReturn(genre2);
        //Mockito.when(bookJdbcDao.update(bookForUpdateCheck)).thenReturn(1);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);
        Mockito.when(evaluatingDataService.isThereAreOnlyDigitsInText(Mockito.any())).thenReturn(true);

        Book actualBook = booksEditorService.editBook("1","New name","2","2");

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(bookExpected);*/
    }

    @Test
    @Description("корректно выдавать книгу по идентификатору")
    void getBookById() {

        final Author author1 = new Author(1L, AUTHOR_1);
        final Genre genre1 = new Genre(1L, GENRE_1);
        final Book bookExpected = new Book(1L, "Book1", List.of(author1), List.of(genre1), Collections.emptyList());

        Mockito.when(bookJpaDao.getById(1L)).thenReturn(Optional.of(bookExpected));
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);

        Book bookFromService = booksEditorService.getBookById("1");

        assertThat(bookFromService).usingRecursiveComparison().isEqualTo(bookExpected);

    }

    @Test
    @Description("должен корректно удалять книгу и при запросе книги по идентификатору удалённой книги вызывать исключение")
    void deleteBookByIdAndThrowRuntimeExceptionOnGetByThisId() {

        Mockito.when(bookJpaDao.delete(1)).thenReturn(1);
        Mockito.when(evaluatingDataService.isTextNotNullAndNotBlank(Mockito.any())).thenReturn(true);
        Mockito.when(bookJpaDao.getById(1)).thenThrow(EmptyResultDataAccessException.class);

        int deletedRecCount = booksEditorService.deleteBookById("1");

        assertThat(deletedRecCount).usingRecursiveComparison().isEqualTo(EXPECTED_DELETED_RECORD_COUNT);

        assertThatCode( () -> booksEditorService.getBookById("1")).isInstanceOf(RuntimeException.class);

    }
}