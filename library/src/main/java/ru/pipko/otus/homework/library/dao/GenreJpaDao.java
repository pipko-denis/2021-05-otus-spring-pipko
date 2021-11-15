package ru.pipko.otus.homework.library.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pipko.otus.homework.library.exceptions.DaoRuntimeException;
import ru.pipko.otus.homework.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "jpa-dao-enabled", havingValue = "true", matchIfMissing = true)
public class GenreJpaDao implements GenreDao{

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("SELECT e FROM Genre e ORDER BY e.name",Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class,id));
    }

    @Override
    public List<Genre> getById(List<Long> ids) {
        TypedQuery<Genre> query = em.createQuery("SELECT e FROM Genre e Where id IN :id",Genre.class);
        query.setParameter("id",ids);
        return query.getResultList();
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            throw new DaoRuntimeException("Attempt to add existing record, id = "+genre.getId());
        }
        return genre;
    }
}
