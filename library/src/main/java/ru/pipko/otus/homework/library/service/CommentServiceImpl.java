package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.CommentDao;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Comment;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY = "Comment is incorrect, it should not be empty";
    public static final String ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_EMPTY = " id is incorrect! It should contain only empty!";
    public static final String THERE_ARE_NO_COMMENTS_WITH_ID = "There are no comments with id=";

    private final CommentDao commentDao;

    private final EvaluatingDataService evaluatingService;

    private final BooksEditorService booksService;

    @Override
    public void deleteComment(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException("Comment"+ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_EMPTY);

        final long longId = Long.parseLong(id);
        final long deletedRecCount = commentDao.delete(longId);

        if (deletedRecCount == 0)
            throw new RuntimeException(THERE_ARE_NO_COMMENTS_WITH_ID+id);

    }

    @Override
    public Comment addComment(String bookId, String commentText) {
        if ( ! evaluatingService.isTextNotNullAndNotBlank(commentText) )
            throw new RuntimeException(COMMENT_IS_INCORRECT_IT_SHOULD_NOT_BE_EMPTY);
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(bookId) )
            throw new RuntimeException("Comment "+ID_IS_INCORRECT_IT_SHOULD_CONTAIN_ONLY_EMPTY);

        final Book book = booksService.getBookById(bookId);

        if (book == null){
            throw new RuntimeException("Service returned null instead of book, comment muss contain reference on book, id of requested book:" + bookId);
        }

        final Comment comment = new Comment(commentText,book);

        return commentDao.insert(comment);
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
