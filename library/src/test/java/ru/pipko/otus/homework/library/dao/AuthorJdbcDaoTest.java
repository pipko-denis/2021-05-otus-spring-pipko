package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorJpaDao.class)
@DisplayName("AuthorJdbcDao должен")
class AuthorJdbcDaoTest {

    @Autowired
    private AuthorDao authorDao;

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

        Optional<Author> authorOptional = authorDao.getById(1);

        assertThat(authorOptional.isEmpty()).isEqualTo(false);

        var authorFromDb = authorOptional.get();

        assertThat(authorFromDb).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }


    @Test
    @DisplayName("добавлять автора и устанавливать идентификатор переданому в DAO объекту")
    void shouldInsertAuthorAndSetId() {
        final Author addedAuthor = authorDao.insert(new Author("Added Author name"));

        assertThat(addedAuthor.getId()).isNotNull().isPositive();

        final var authorOptional = authorDao.getById(addedAuthor.getId());

        assertThat(authorOptional.isEmpty()).isEqualTo(false);

        final Author authorFromDb = authorOptional.get();

        assertThat(authorFromDb).usingRecursiveComparison().isEqualTo(addedAuthor);
    }


}