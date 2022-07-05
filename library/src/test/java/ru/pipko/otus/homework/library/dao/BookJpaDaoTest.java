package ru.pipko.otus.homework.library.dao;

import org.assertj.core.util.Lists;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.pipko.otus.homework.library.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest
@Import(BookJpaDao.class)
@DisplayName("Репозиторий BookJpaDao для получения книг должен")
class BookJpaDaoTest {

    private static final int EXPECTED_QUERIES_COUNT = 2;
    private static final int EXPECTED_BOOKS_COUNT = 4;
    private static final String DELETE_TEST_BOOK_NAME = "Delete test book name";
    private static final long GET_BY_ID_PRIMARY_KEY = 1;
    private static final String INSERT_TEST_BOOK_NAME = "Insert test book name";
    public static final String BOOK_NAME_FOR_UPDATE = "Book name for update";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookJpaDao bookDao;

    @Test
    @DisplayName("должен загрузить все записи с правильным количеством запросов")
    void shouldLoadAllRecordsWithCorrectQueriesCount(){
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> books = bookDao.getAll();
        assertThat(books)
                .isNotNull()
                .hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(book -> book.getName() != null)
                .allMatch(book -> !book.getName().equals(""));
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @Test
    @DisplayName("корректно получать книгу по идентификатору")
    void shouldGetBookById() {
        final Book bookFromEntityManager = em.find(Book.class, GET_BY_ID_PRIMARY_KEY);
        final Optional<Book> bookFromDao = bookDao.getById(GET_BY_ID_PRIMARY_KEY);
        assertThat(bookFromDao).isNotEmpty().get().usingRecursiveComparison().isEqualTo(bookFromEntityManager);
    }



    @Test
    @DisplayName(" должен корректно изменять информацию о книге")
    void shouldCorrectUpdateBook() {
        Book bookForUpdate = em.find(Book.class, GET_BY_ID_PRIMARY_KEY);
        String oldName = bookForUpdate.getName();
        em.detach(bookForUpdate);

        bookForUpdate.setName(BOOK_NAME_FOR_UPDATE);
        bookDao.update(bookForUpdate);
        Book updatedStudent = em.find(Book.class, GET_BY_ID_PRIMARY_KEY);

        assertThat(updatedStudent.getName()).isNotEqualTo(oldName).isEqualTo(BOOK_NAME_FOR_UPDATE);
    }

    @Test
    @DisplayName("корректно добавлять книгу")
    void insert() {
        Book book = new Book(INSERT_TEST_BOOK_NAME, Lists.emptyList(),Lists.emptyList());
        book.setComments(Lists.emptyList());

        bookDao.insert(book);
        assertThat(book.getId()).isNotNull().isGreaterThan(0);

        final Book bookFromEntityManager = em.find(Book.class, book.getId());

        assertThat(bookFromEntityManager).isNotNull().hasNoNullFieldsOrProperties().matches(s -> !s.getName().equals(""));
    }

    @Test
    @DisplayName("корректно удалять книгу по её идентификатору, но не трогать жанры и авторов")
    void delete() {
        final Book bookFromEntityManager = em.persist(new Book(DELETE_TEST_BOOK_NAME,Lists.emptyList(),Lists.emptyList()));
        Long deletedBookId = bookFromEntityManager.getId();
        assertThat(deletedBookId).isNotNull().isGreaterThan(0);
        em.detach(bookFromEntityManager);

        bookDao.delete(deletedBookId);
        Book deletedBook = em.find(Book.class, deletedBookId);
        assertThat(deletedBook).isNull();

        assertThatCode( () -> bookDao.delete(deletedBookId)).isInstanceOf(Throwable.class);
    }

    @Test
    void getBooksCountByBookId() {
    }

    @Test
    void getBookCommentsCount() {
    }
}