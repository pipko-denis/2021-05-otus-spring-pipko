package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class GenreJpaDao implements GenreDao{

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT e FROM Genre e ORDER BY e.name",Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre getById(long id) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT e FROM Genre e Where e.id = :id",Genre.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getById(List<Long> ids) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT e FROM Genre e Where id IN :id",Genre.class);
        query.setParameter("id",ids);
        return query.getResultList();
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
        }
        return genre;
    }
}
