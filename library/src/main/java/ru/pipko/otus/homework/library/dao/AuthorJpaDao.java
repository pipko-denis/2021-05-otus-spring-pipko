package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class AuthorJpaDao implements AuthorDao{

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Transactional
    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT e FROM Author e order by e.name",Author.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Author getById(long id) {
        return null;
    }

    @Transactional
    @Override
    public List<Author> getByName(String name) {
        return null;
    }

    @Transactional
    @Override
    public int insert(Author author) {
        return 0;
    }

    @Transactional
    @Override
    public int delete(long id) {
        return 0;
    }
}
