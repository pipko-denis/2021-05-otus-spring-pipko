package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class BookJpaDao implements BookDao{

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Transactional
    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("SELECT e FROM Book e ORDER BY e.name",Book.class).getResultList();
    }

    @Transactional
    @Override
    public Book getById(long id) {
        return null;
    }

    @Transactional
    @Override
    public int insert(Book book) {
        return 0;
    }

    @Transactional
    @Override
    public int update(Book book) {
        return 0;
    }

    @Transactional
    @Override
    public int delete(long id) {
        return 0;
    }

    @Transactional
    @Override
    public Integer getBooksCountByAuthorId(long authorId) {
        return null;
    }
}
