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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorJpaDao.class)
@DisplayName("Репозиторий AuthorJpaDao для работы с авторами должен")
class AuthorJpaDaoTest {

    public static final long GET_BY_ID_PRIMARY_KEY = 1L;
    public static final String INSERT_TEST_AUTHOR_NAME = "Insert test author name";
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
        List<Author> allAuthorsFromDao = authorDao.getAll();
        assertThat(allAuthorsFromDao).isNotEmpty().hasSizeGreaterThan(1);


        List<Long> authorIds = allAuthorsFromDao.stream()
                .map(Author::getId)
                .filter(authorId -> authorId % 2 != 0)
                .limit(2)
                .collect(Collectors.toList());

        List<Author> authorsFromDaoToCheck = authorDao.getById(authorIds);
        authorsFromDaoToCheck.forEach( authorFromDao ->
                //em.find(Author.class,author.getId()).getName().equals(author.getName())
                {
                    Author authorFromEm = em.find(Author.class, authorFromDao.getId());
                    assertThat(authorFromEm).isNotNull()
                            .matches(e -> e.getName() != null)
                            .matches(e -> ! e.getName().equals(""))
                            .matches(e -> e.getName().equals(authorFromDao.getName()));

                }
        );
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
        Author author = new Author(INSERT_TEST_AUTHOR_NAME);

        authorDao.insert(author);
        assertThat(author.getId()).isNotNull().isGreaterThan(0);

        final Author authorFromEntityManager = em.find(Author.class, author.getId());

        assertThat(authorFromEntityManager).isNotNull().hasNoNullFieldsOrProperties().matches(s -> !s.getName().equals(""));
    }

    @Test
    @DisplayName("корректно удалять автора по его идентификатору")
    void delete() {
        final Author authorFromEntityManager = em.find(Author.class, GET_BY_ID_PRIMARY_KEY);
        assertThat(authorFromEntityManager).isNotNull();
        em.detach(authorFromEntityManager);

        authorDao.delete(GET_BY_ID_PRIMARY_KEY);
        Author deletedStudent = em.find(Author.class, GET_BY_ID_PRIMARY_KEY);

        assertThat(deletedStudent).isNull();
    }
}