package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.dto.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class BookJpaDao implements BookDao{

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    @Override
    public List<Book> getAll() {
        return em.createQuery("SELECT e FROM Book e ORDER BY e.name",Book.class).getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class,id));
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == null){
            em.persist(book);
        } else{
            throw new RuntimeException("Attempt to add existing record, id = "+book.getId());
        }
        return  book;
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null){
            throw new RuntimeException("Book should have an id!");
        }else{
            return em.merge(book);
        }
    }

    @Override
    public int delete(long id) {
        final Query query = em.createQuery("DELETE FROM Book e WHERE e.id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }

    @Override
    public long getBooksCountByAuthorId(long authorId) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Book e " +
                "JOIN e.authors as a " +
                "WHERE a.id = :authorId", Long.class);
        query.setParameter("authorId",authorId);
        return query.getSingleResult();
    }

    @Override
    public List<BookComment> getBookCommentsCount(int limit){
        final TypedQuery<BookComment> query = em
                .createQuery("SELECT new ru.pipko.otus.homework.library.dto.BookComment(e.name, COUNT(c)) " +
                                "FROM Book e " +
                                "left join e.comments c " +
                                "GROUP BY e " +
                                "ORDER BY COUNT(c) desc"
                        ,BookComment.class);
        return query.getResultList().stream().limit(limit).collect(Collectors.toList());
    }
}
