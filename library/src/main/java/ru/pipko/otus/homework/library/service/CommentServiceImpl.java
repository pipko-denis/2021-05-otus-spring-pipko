package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.CommentDao;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Comment;
import ru.pipko.otus.homework.library.exceptions.ServiceRuntimeException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY = "Comment is incorrect, it should not be empty";
    public static final String ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_DIGITS = " id is incorrect! It should contain only digits!";
    public static final String THERE_ARE_NO_COMMENTS_WITH_ID = "There are no comments with id=";

    private final CommentDao commentDao;

    private final EvaluatingDataService evaluatingService;

    private final BooksEditorService booksService;

    @Transactional
    @Override
    public void deleteComment(String id) {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id, ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_DIGITS);

        final long longId = Long.parseLong(id);
        final long deletedRecCount = commentDao.delete(longId);

        if (deletedRecCount == 0)
            throw new ServiceRuntimeException(THERE_ARE_NO_COMMENTS_WITH_ID+id);

    }

    @Transactional
    @Override
    public Comment addComment(String bookId, String commentText) {
        if ( ! evaluatingService.isTextNotNullAndNotBlank(commentText) )
            throw new ServiceRuntimeException(COMMENT_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(bookId, ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_DIGITS);

        final Book book = booksService.getBookById(bookId);

        if (book == null){
            throw new ServiceRuntimeException("Service returned null instead of book, comment muss contain reference on book, id of requested book:" + bookId);
        }

        final Comment comment = new Comment(commentText,book);

        return commentDao.insert(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getByBookId(String id) {

        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,"Book id is incorrect! It should contains only digits!");

        final long bookId = Long.parseLong(id);
        try {
            return commentDao.getByBookId(bookId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new ServiceRuntimeException("There are no authors with id="+id+"!");
        }

    }
}
