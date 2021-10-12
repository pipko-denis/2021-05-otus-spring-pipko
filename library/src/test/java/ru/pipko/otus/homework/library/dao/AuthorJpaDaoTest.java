package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorJpaDao.class)
@DisplayName("Репозиторий AuthorJpaDao для работы с авторами должен")
class AuthorJpaDaoTest {

    public static final long GET_BY_ID_PRIMARY_KEY = 1L;
    @Autowired
    private AuthorJpaDao authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен загрузить все записи с правильным количеством запросов")
    void shouldLoadAllRecordsWithCorrectQueriesCount(){

    }


    @Test
    @DisplayName("корректно получать автора по идентификатору")
    void getById() {
        final Author authorFromEntityManager = em.find(Author.class, GET_BY_ID_PRIMARY_KEY);
        final Optional<Author> authorFromDao = authorDao.getById(GET_BY_ID_PRIMARY_KEY);
        assertThat(authorFromDao).isNotEmpty().get().usingRecursiveComparison().isEqualTo(authorFromEntityManager);
    }

    @Test
    @DisplayName("корректно получать список авторов по их идентификаторам")
    void getAuthorsListByIds() {

    }

    @Test
    @DisplayName("корректно получать список авторов по его части имени")
    void getByName() {
        final Author authorFromEntityManager = em.find(Author.class, GET_BY_ID_PRIMARY_KEY);
        final List<Author> students = authorDao.findByName(authorFromEntityManager.getName());
        assertThat(students).containsOnlyOnce(authorFromEntityManager);
    }

    @Test
    @DisplayName("корректно добавлять автора")
    void insert() {

    }

    @Test
    @DisplayName("корректно удалять автора по его идентификатору")
    void delete() {
    }
}