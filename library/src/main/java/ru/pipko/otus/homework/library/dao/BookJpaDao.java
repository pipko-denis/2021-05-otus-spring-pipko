package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class BookJpaDao implements BookDao{

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em){
        this.em = em;
    }


    @Transactional
    @Override
    public List<Book> getAll() {
        return em.createQuery("SELECT e FROM Book e ORDER BY e.name",Book.class).getResultList();
    }

    @Transactional
    @Override
    public Book getById(long id) {
        return em.createQuery("SELECT e FROM Book e WHERE e.id = :id ",Book.class).setParameter("id",id).getSingleResult();
    }

    @Transactional
    @Override
    public Book insert(Book book) {
        if (book.getId() == null){
            em.persist(book);
        }
        return  book;
    }

    @Transactional
    @Override
    public Book update(Book book) {
        if (book.getId() == null){
            throw new RuntimeException("Book should have an id!");
        }else{
            return em.merge(book);
        }
    }

    @Transactional
    @Override
    public int delete(long id) {
        Query query = em.createQuery("DELETE FROM Books WHERE id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }

    @Transactional
    @Override
    public long getBooksCountByAuthorId(long authorId) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Books e WHERE e.authors.id = :authorId", Long.class);
        query.setParameter("authorId",authorId);
        return query.getSingleResult();
    }
}
