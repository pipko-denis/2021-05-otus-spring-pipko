package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.dto.BookComment;
import ru.pipko.otus.homework.library.exceptions.DaoRuntimeException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookJpaDao implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("SELECT e " +
                "FROM Book e " +
                "ORDER BY e.name", Book.class);

        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            throw new DaoRuntimeException("Attempt to add existing record, id = " + book.getId());
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null) {
            throw new DaoRuntimeException("Book should have an id!");
        } else {
            return em.merge(book);
        }
    }

    @Override
    public int delete(long id) {
        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new DaoRuntimeException("Deletion impossible: book with id:"+id+" not found!");
        }
        em.remove(book);
        return 1;
/*      CascadeType.ALL doesn't work with query!!!
        final Query query = em.createQuery("DELETE FROM Book e WHERE e.id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
 */
    }

    @Override
    public long getBooksCountByAuthorId(long authorId) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Book e " +
                "JOIN e.authors as a " +
                "WHERE a.id = :authorId", Long.class);
        query.setParameter("authorId", authorId);
        return query.getSingleResult();
    }

    @Override
    public List<BookComment> getBookCommentsCount(int limit) {
        final TypedQuery<BookComment> query = em
                .createQuery("SELECT new ru.pipko.otus.homework.library.dto.BookComment(e.name, COUNT(c)) " +
                                "FROM Book e " +
                                "left join e.comments c " +
                                "GROUP BY e " +
                                "ORDER BY COUNT(c) desc"
                        , BookComment.class);
        return query.getResultList().stream().limit(limit).collect(Collectors.toList());
    }
}
