package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorJdbcDao.class)
@DisplayName("AuthorJdbcDao должен")
class AuthorJdbcDaoTest {

    @Autowired
    private AuthorJdbcDao authorDao;

    @Test
    @DisplayName("выдавать список авторов правильной длинны, который должен включать одну определённого автора")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldGetCorrectListOfAuthorsWithOneConcrete() {
        List<Author> listAuthors = authorDao.getAll();
        assertThat(listAuthors.size()).isGreaterThan(0);

        final Author expected = new Author(1L, "Author1");

        assertThat(listAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expected);
    }

    @Test
    @DisplayName("возвращать автора по его идентификатору")
    void shouldGetAuthorById() {
        final Author expectedAuthor = new Author(1L, "Author1");

        final Author authorFromDb = authorDao.getById(1);

        assertThat(authorFromDb).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }


    @Test
    @DisplayName("добавлять автора и устанавливать идентификатор переданому в DAO объекту")
    void shouldInsertAuthorAndSetId() {
        final Author author = new Author("Added Author name");
        final Author addedAuthor = authorDao.insert(author);

        assertThat(author.getId()).isNotNull().isPositive();

        final Author authorFromDb = authorDao.getById(author.getId());

        assertThat(authorFromDb).usingRecursiveComparison().isEqualTo(author);
    }


}