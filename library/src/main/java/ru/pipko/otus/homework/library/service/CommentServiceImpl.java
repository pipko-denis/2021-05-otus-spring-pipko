package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.CommentDao;
import ru.pipko.otus.homework.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    private final EvaluatingDataService evaluatingService;

    @Override
    public int deleteComment(long id) {
        return 0;
    }

    @Override
    public Comment addComment(long bookId, String commentText) {
        return null;
    }

    @Override
    public List<Comment> getByBookId(String id) {

        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException("Author id is incorrect! It should contains only digits!");

        final long bookId = Long.parseLong(id);
        try {
            return commentDao.getByBookId(bookId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("There are no authors with id="+id+"!");
        }

    }
}
