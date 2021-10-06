package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreJpaDao.class)
@DisplayName("GenreJdbcDao должен")
class GenreJdbcDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("выдавать список жанров правильной длинны, который должен включать одну определённую жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldGetCorrectListOfGenresWithOneConcrete() {
        List<Genre> listGenres = genreDao.getAll();
        assertThat(listGenres.size()).isGreaterThan(0);

        final Genre expected = new Genre(1L, "Genre1");

        assertThat(listGenres)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expected);
    }

    @Test
    @DisplayName("возвращать жанр по идентификатору")
    void shouldGetGenreById() {
        final Genre expectedGenre = new Genre(1L, "Genre1");

        final Genre genreFromDb = genreDao.getById(1).get();

        assertThat(genreFromDb).usingRecursiveComparison().isEqualTo(expectedGenre);
    }


    @Test
    @DisplayName("добавлять жанр и устанавливать идентификатор переданому в DAO объекту")
    void shouldInsertGenreAndSetId() {
        final Genre genre = genreDao.insert(new Genre("Added Genre name"));

        assertThat(genre.getId()).isNotNull().isPositive();

        assertThat(genre.getId()).isNotNull().isPositive();

        final Genre genreFromDb = genreDao.getById(genre.getId()).get();

        assertThat(genreFromDb).usingRecursiveComparison().isEqualTo(genre);
    }


}