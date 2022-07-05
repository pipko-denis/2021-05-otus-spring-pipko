package ru.pipko.otus.homework.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.exceptions.DaoRuntimeException;
import ru.pipko.otus.homework.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentJpaDao implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() == null){
            em.persist(comment);
        }else{
            throw new DaoRuntimeException("Attempt to add existing record, id = "+comment.getId());
        }
        return comment;
    }

    @Override
    public long delete(long id) {
        return em.createQuery("DELETE FROM Comment e WHERE e.id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public List<Comment> getByBookId(long id) {
        TypedQuery<Comment> query = em.createQuery(
                "SELECT e FROM Comment e " +
                        "join fetch e.book " +
                        "WHERE e.book.id = :id", Comment.class)
                .setParameter("id",id);

        return query.getResultList();
    }
}
