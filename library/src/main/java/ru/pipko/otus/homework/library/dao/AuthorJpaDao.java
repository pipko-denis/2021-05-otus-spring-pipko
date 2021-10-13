package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class AuthorJpaDao implements AuthorDao{

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("SELECT e FROM Author e order by e.name",Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class,id));
    }

    @Override
    public List<Author> getById(List<Long> ids) {
        TypedQuery<Author> query = em.createQuery("SELECT e FROM Author e Where id IN :id",Author.class);
        query.setParameter("id",ids);
        return query.getResultList();
    }

    @Override
    public List<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery("SELECT e FROM Author e Where e.name like :name order by e.name",Author.class);
        query.setParameter("name","%"+name+"%");
        return query.getResultList();
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() == null){
            em.persist(author);
        } else {
            throw new RuntimeException("Attempting to add existing record, id = "+author.getId());
        }
        return author;
    }

    @Override
    public int delete(long id) {
        Query query = em.createQuery("DELETE FROM Author WHERE id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }
}
