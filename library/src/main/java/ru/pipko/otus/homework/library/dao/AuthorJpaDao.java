package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
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



    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT e FROM Author e order by e.name",Author.class);
        return query.getResultList();
    }

    @Override
    public Author getById(long id) {
        return null;
    }

    @Override
    public List<Author> getByName(String name) {
        return null;
    }

    @Override
    public int insert(Author author) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
