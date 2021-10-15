package ru.pipko.otus.homework.library.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreJpaDao.class)
@DisplayName("Репозиторий GenreJpaDao для работы с жанрами должен ")
class GenreJpaDaoTest {

    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final int EXPECTED_GENRES_COUNT = 4;
    private static final long GET_BY_ID_PRIMARY_KEY = 1;
    private static final String INSERT_TEST_GENRE_NAME = "Insert test genre name";
    @Autowired
    GenreJpaDao genreJpaDao;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("должен загрузить все записи с правильным количеством запросов")
    void shouldLoadAllRecordsWithCorrectQueriesCount() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Genre> genres = genreJpaDao.getAll();
        assertThat(genres)
                .isNotNull()
                .hasSize(EXPECTED_GENRES_COUNT)
                .allMatch(genre -> genre.getName() != null)
                .allMatch(genre -> !genre.getName().equals(""));
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @Test
    @DisplayName("корректно получать жанр по идентификатору")
    void shouldGetGenreById() {
        final Genre genreFromEntityManager = em.find(Genre.class, GET_BY_ID_PRIMARY_KEY);
        final Optional<Genre> genreFromDao = genreJpaDao.getById(GET_BY_ID_PRIMARY_KEY);
        assertThat(genreFromDao).isNotEmpty().get().usingRecursiveComparison().isEqualTo(genreFromEntityManager);
    }

    @Test
    @DisplayName("корректно получать список жанров по их идентификаторам")
    void getGenresListByIds() {
        List<Genre> allGenresFromDao = genreJpaDao.getAll();
        assertThat(allGenresFromDao).isNotEmpty().hasSizeGreaterThan(EXPECTED_QUERIES_COUNT);


        List<Long> genreIds = allGenresFromDao.stream()
                .map(Genre::getId)
                .filter(genreId -> genreId % 2 != 0)
                .limit(2)
                .collect(Collectors.toList());

        List<Genre> genresFromDaoToCheck = genreJpaDao.getById(genreIds);
        genresFromDaoToCheck.forEach( genreFromDao ->
                        //em.find(genre.class,genre.getId()).getName().equals(genre.getName())
                {
                    Genre genreFromEm = em.find(Genre.class, genreFromDao.getId());
                    assertThat(genreFromEm).isNotNull()
                            .matches(e -> e.getName() != null)
                            .matches(e -> ! e.getName().equals(""))
                            .matches(e -> e.getName().equals(genreFromDao.getName()));

                }
        );
    }

    @Test
    @DisplayName("корректно добавлять жанр")
    void insert() {
        Genre genre = new Genre(INSERT_TEST_GENRE_NAME);

        genreJpaDao.insert(genre);
        assertThat(genre.getId()).isNotNull().isGreaterThan(0);

        final Genre genreFromEntityManager = em.find(Genre.class, genre.getId());

        assertThat(genreFromEntityManager).isNotNull().hasNoNullFieldsOrProperties().matches(s -> !s.getName().equals(""));
    }
}