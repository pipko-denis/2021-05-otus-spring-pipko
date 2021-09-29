package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        TypedQuery<Author> query = entityManager.createQuery("SELECT e FROM Author e Where id = :id",Author.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getById(List<Long> ids) {
        TypedQuery<Author> query = entityManager.createQuery("SELECT e FROM Author e Where id IN :id",Author.class);
        query.setParameter("id",ids);
        return query.getResultList();
    }

    @Override
    public List<Author> getByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("SELECT e FROM Author e Where e.name like '%:name%' order by e.name",Author.class);
        query.setParameter("name",name);
        return query.getResultList();
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() == null){
            entityManager.persist(author);
        }
        return author;
    }

    @Override
    public int delete(long id) {
        Query query = entityManager.createQuery("DELETE FROM Author WHERE id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }
}
