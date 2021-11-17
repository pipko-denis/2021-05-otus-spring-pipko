package ru.pipko.otus.homework.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.exceptions.EvaluatingException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Сервис BooksEditorServiceImpl должен")
@SpringBootTest
@Import({BooksEditorServiceImpl.class, EvaluatingDataServiceImpl.class,GenreEditorServiceImpl.class,AuthorEditorServiceImpl.class})
class BooksEditorServiceImplTest {

    public static final String AUTHOR_1 = "Author1";
    public static final Author AUTHOR_WITH_ID_1 = new Author(1L, AUTHOR_1);

    public static final String GENRE_1 = "Genre1";
    public static final Genre  GENRE_WITH_ID_1 = new Genre(1L, GENRE_1);

    public static final String TEXT_TO_PRODUCE_ERROR = "text to produce error";
    public static final String NOT_BLANK_BOOK_NAME = "Not blank book name";
    public static final String BLANK_TEXT_TO_PRODUCE_ERROR = "";

    public static final Book   BOOK_WITH_ID_ONE_EMPTY_LISTS = new Book(1L, "Book1", Collections.emptyList(), Collections.emptyList(), Collections.emptyList());


    @Autowired
    private BooksEditorService booksEditorService;

    @Autowired
    private EvaluatingDataService evaluatingDataService;

    @Autowired
    private GenreEditorService genreEditorService;

    @Autowired
    private AuthorEditorService authorEditorService;

    @MockBean
    private AuthorDao authorJdbcDao;

    @MockBean
    private BookDao bookJpaDao;

    @MockBean
    private GenreDao genreJdbcDao;

    @Configuration
    static class Config {

    }


    @Test
    @DisplayName("не позволять создавать книгу с не корректным именем")
    void addBookShouldProduceErrorBecauseOfIncorrectName() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode( () -> booksEditorService.addBook(BLANK_TEXT_TO_PRODUCE_ERROR,"2","2")).isInstanceOf(EvaluatingException.class);
    }


    @Test
    @DisplayName("не позволять создавать книгу с не корректным Id автора")
    void addBookShouldProduceErrorBecauseOfIncorrectAuthorId() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode(() -> booksEditorService.addBook(NOT_BLANK_BOOK_NAME, TEXT_TO_PRODUCE_ERROR, "2")).isInstanceOf(EvaluatingException.class);
    }

    @Test
    @DisplayName("не позволять создавать книгу с не корректным Id жанра")
    void addBookShouldProduceErrorBecauseOfIncorrectGenreId() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode( () -> booksEditorService.addBook(NOT_BLANK_BOOK_NAME, "2",TEXT_TO_PRODUCE_ERROR)).isInstanceOf(EvaluatingException.class);
    }


    @Test
    @DisplayName("не позволять изменять книгу с не корректным именем")
    void editBookShouldProduceErrorBecauseOfIncorrectName() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode( () -> booksEditorService.editBook("1", BLANK_TEXT_TO_PRODUCE_ERROR,"2","2")).isInstanceOf(EvaluatingException.class);
    }

    @Test
    @DisplayName("не позволять изменять книгу с не корректным Id")
    void editBookShouldProduceErrorBecauseOfIncorrectId() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode( () -> booksEditorService.editBook(TEXT_TO_PRODUCE_ERROR,NOT_BLANK_BOOK_NAME,"2","2")).isInstanceOf(EvaluatingException.class);

        assertThatCode( () -> booksEditorService.editBook(BLANK_TEXT_TO_PRODUCE_ERROR, NOT_BLANK_BOOK_NAME,"2","2")).isInstanceOf(EvaluatingException.class);
    }


    @Test
    @DisplayName("не позволять изменять книгу с не корректным Id автора")
    void editBookShouldProduceErrorBecauseOfIncorrectAuthorId() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode(() -> booksEditorService.editBook("1", NOT_BLANK_BOOK_NAME, TEXT_TO_PRODUCE_ERROR, "2")).isInstanceOf(EvaluatingException.class);
    }

    @Test
    @DisplayName("не позволять изменять книгу с не корректным Id жанра")
    void editBookShouldProduceErrorBecauseOfIncorrectGenreId() {
        Mockito.when(bookJpaDao.getById(Mockito.anyLong())).thenReturn(Optional.of(BOOK_WITH_ID_ONE_EMPTY_LISTS));
        Mockito.when(authorJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(AUTHOR_WITH_ID_1));
        Mockito.when(genreJdbcDao.getById(Mockito.anyList())).thenReturn(List.of(GENRE_WITH_ID_1));
        assertThatCode( () -> booksEditorService.editBook("1",NOT_BLANK_BOOK_NAME, "2",TEXT_TO_PRODUCE_ERROR)).isInstanceOf(EvaluatingException.class);
    }


}