package ru.pipko.otus.homework.library.dao;

import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentJpaDao implements CommentDao {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() == null){
            em.persist(comment);
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
        TypedQuery<Comment> query = em.createQuery("SELECT e FROM Comment e WHERE e.book.id = :id", Comment.class)
                .setParameter("id",id);
        return query.getResultList();
    }
}
