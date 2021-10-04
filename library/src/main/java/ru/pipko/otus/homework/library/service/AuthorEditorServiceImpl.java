package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorEditorServiceImpl implements AuthorEditorService {

    public static final String AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS = "Author id is incorrect! It should contains only digits!";
    public static final String THERE_ARE_NO_AUTHORS_WITH_ID = "There are no authors with id=";
    private final EvaluatingDataService evaluatingService;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Transactional
    @Override
    public Author getAuthorById(String id)  {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException(AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long authorId = Long.parseLong(id);
        try {
            return authorDao.getById(authorId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException(THERE_ARE_NO_AUTHORS_WITH_ID +id+"!");
        }
    }

    @Transactional
    @Override
    public List<Author> getAuthorsById(String[] ids)  {

        evaluatingService.checkArrayOnDigitsThrowException("Author", ids);

        final List<Long> idsLong = Arrays.stream(ids).map(id -> Long.valueOf(id)).collect(Collectors.toList());

        return authorDao.getById(idsLong);
    }

    @Transactional
    @Override
    public int deleteAuthorById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException(AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long authorId = Long.parseLong(id);
        long authorsBookCount = bookDao.getBooksCountByAuthorId(authorId);

        if ( authorsBookCount > 0 )
            throw new RuntimeException("You can't delete author with id = "+id+"! " +
                "It's used in "+authorsBookCount+" books! " +
                "Try to delete books or change there's authors first");

        final int deletedRecCount = authorDao.delete(authorId);

        if (deletedRecCount == 0)
            throw new RuntimeException(THERE_ARE_NO_AUTHORS_WITH_ID+id);

        return deletedRecCount;
    }

    @Transactional
    @Override
    public Author addAuthor(Author author) {
        if (author == null) {
            throw new RuntimeException("Service error: author is null!");
        }
        if ( ! evaluatingService.isTextNotNullAndNotBlank(author.getName()) )
            throw new RuntimeException("Author name should not be empty");

        return authorDao.insert(author);
    }

    @Transactional
    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

}
