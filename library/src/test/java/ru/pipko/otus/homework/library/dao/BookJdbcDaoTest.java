package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("BookJdbcDao должен")
@JdbcTest()
@Import(BookDao.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookJdbcDaoTest {

    public static final int EXPECTED_UPDATED_BOOKS_COUNT = 1;
    public static final int EXPECTED_ADDED_BOOKS_COUNT = 1;
    public static final int EXPECTED_DELETED_BOOKS_COUNT = 1;
    private static final Predicate<Book> PREDICATE_BOOK_WITH_ID_AND_AUTHOR_AND_GENRE = book ->
            (book.getId() != null)
                    && (book.getAuthors() != null) && (!book.getAuthors().isEmpty())
                    && (book.getGenres() != null) && (!book.getGenres().isEmpty());


    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("выдавать список книг правильной длинны, который должен включать одну определённую книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldGetCorrectListOfBooksWithOneConcrete() {
        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(0);

        final Book expectedBook = new Book(1L, "Book1", List.of(new Author(1L, "Author1")), List.of(new Genre(1L, "Genre1")));

        assertThat(listBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);

    }

    @Test
    @DisplayName("возвращать книгу по идентификатору")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldGetBookById() {
        final Book expectedBook = new Book(1L, "Book1", List.of(new Author(1L, "Author1")), List.of(new Genre(1L, "Genre1")));

        final Book bookFromDb = bookDao.getById(1);

        assertThat(bookFromDb).usingRecursiveComparison().isEqualTo(expectedBook);
    }


    @Test
    @DisplayName("бросать исключение если книга не найдена")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldThrowErrorOnGetBookByAbsentId() {

        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(0);

        long maxId = listBooks.stream().max(Comparator.comparing(Book::getId)).get().getId() + 1;

        assertThatCode(() -> bookDao.getById(maxId)).isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Test
    @DisplayName("добавлять книгу и устанавливать идентификатор переданому в DAO объекту")
    void shouldInsertBookAndSetId() {

        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(0);

        final Book bookWithAuthorAndGenre = listBooks.stream().filter(PREDICATE_BOOK_WITH_ID_AND_AUTHOR_AND_GENRE)
                .findFirst().orElse(null);
        assertThat(bookWithAuthorAndGenre).isNotNull();


        final Book book = new Book("Added book name", bookWithAuthorAndGenre.getAuthors(), bookWithAuthorAndGenre.getGenres());
        final int addedRecordCount = bookDao.insert(book);

        assertThat(addedRecordCount).isEqualTo(EXPECTED_ADDED_BOOKS_COUNT);

        assertThat(book.getId()).isNotNull().isPositive();

        final Book bookFromDb = bookDao.getById(book.getId());

        assertThat(bookFromDb).usingRecursiveComparison().isEqualTo(book);
    }


    @Test
    @DisplayName("корректно изменять данные книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldCorrectChangeBook() {

        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(1);

        final List<Book> listBookWithAuthorAndGenre = listBooks.stream().filter(PREDICATE_BOOK_WITH_ID_AND_AUTHOR_AND_GENRE)
                .collect(Collectors.toList());
        assertThat(listBookWithAuthorAndGenre.size()).isGreaterThan(1);

        final Book bookForEditing = listBookWithAuthorAndGenre.get(0);
        final Long actualAuthorId = bookForEditing.getAuthors().get(0).getId();
        final Long actualGenreId = bookForEditing.getGenres().get(0).getId();

        //final Author newAuthor = listBookWithAuthorAndGenre.stream().filter(book -> book.getAuthors().)
                //.filter(book -> !actualAuthorId.equals(book.getAuthors().getId())).findFirst().get().getAuthors();

        final Author newAuthor = getAuthorsOfBooks(listBooks).stream()
                .filter(author -> !actualAuthorId.equals(author.getId())).findFirst().get();

        final Genre newGenre = getGenresOfBooks(listBooks).stream()
                .filter(genre -> !actualGenreId.equals(genre.getId())).findFirst().get();


//        final Genre newGenre = listBookWithAuthorAndGenre.stream()
//                .filter(book -> !actualGenreId.equals(book.getGenres().get(0).getId())).findFirst().get().getGenres();

        bookForEditing.setAuthors(List.of(newAuthor));

        bookForEditing.setGenres(List.of(newGenre));

        final int updatedRecordCount = bookDao.update(bookForEditing);

        assertThat(updatedRecordCount).isEqualTo(EXPECTED_UPDATED_BOOKS_COUNT);

        final Book bookToCompare = bookDao.getById(bookForEditing.getId());

        assertThat(bookToCompare)
                .usingRecursiveComparison()
                .isEqualTo(bookForEditing);


    }

    @Test
    @DisplayName("корректно удалять книгу по идентификатору")
    void shouldCorrectDeleteBookById() {

        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(0);

        final Long bookToDeleteId = listBooks.stream().map(Book::getId).filter(id -> (id != null)).findFirst().orElse(null);

        assertThat(bookToDeleteId).isNotNull();

        int countDeletedRecords = bookDao.delete(bookToDeleteId);

        assertThat(countDeletedRecords).isEqualTo(EXPECTED_DELETED_BOOKS_COUNT);

        assertThatCode(() -> bookDao.getById(bookToDeleteId)).isInstanceOf(org.springframework.dao.EmptyResultDataAccessException.class);

    }

    private Set<Author> getAuthorsOfBooks(List<Book> listBooks){
        Set<Author> setAuthors = listBooks.stream()
                .map(Book::getAuthors).flatMap(List::stream)
                .filter(author -> (author.getId() != null))
                .collect(Collectors.toSet());
        return setAuthors;
    }

    private Set<Genre> getGenresOfBooks(List<Book> listBooks){
        Set<Genre> setAuthors = listBooks.stream()
                .map(Book::getGenres).flatMap(List::stream)
                .filter(genre -> (genre.getId() != null))
                .collect(Collectors.toSet());
        return setAuthors;
    }


    @Test
    @DisplayName("выдавать правильное количество книг по идентификатору автора")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldGetRightCountOfBooksByAuthorId() {
        final List<Book> listBooks = bookDao.getAll();

        assertThat(listBooks).hasSizeGreaterThan(0);

        // Получаем список уникальных идентификаторов авторов, которые есть в книге
        Set<Long> setAuthorIds = getAuthorsOfBooks(listBooks).stream()
                .map(Author::getId)
                .limit(3).collect(Collectors.toSet());

        Assertions.assertNotEquals(0, setAuthorIds.size());

        setAuthorIds.forEach(authorId -> {
            final long booksCountFromList = listBooks.stream()
                    .filter(book -> (book.getAuthors() != null) &&
                            (book.getAuthors().stream().anyMatch(author -> authorId.equals(author.getId()))))
                            //(book.getAuthors().c)) //&& (authorId.equals(book.getAuthors().getId())))
                    .count();
            final long booksCountFromDao = bookDao.getBooksCountByAuthorId(authorId);
            Assertions.assertEquals(booksCountFromDao, booksCountFromList);
        });


    }
}