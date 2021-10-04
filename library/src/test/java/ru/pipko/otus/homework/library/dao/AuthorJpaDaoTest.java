package ru.pipko.otus.homework.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorJpaDao.class)
@DisplayName("Репозиторий AuthorJpaDao для работы с авторами должен")
class AuthorJpaDaoTest {

    @Test
    @DisplayName("получать полный список авторов")
    void getAll() {
    }

    @Test
    @DisplayName("получать автора по идентификатору")
    void getById() {
    }

    @Test
    @DisplayName("получать список авторов по их идентификаторам")
    void getAuthorsListByIds() {
    }

    @Test
    @DisplayName("получать список авторов по его части имени")
    void getByName() {
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