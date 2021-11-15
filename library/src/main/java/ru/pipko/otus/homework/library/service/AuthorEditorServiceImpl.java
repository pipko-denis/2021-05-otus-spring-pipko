package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.exceptions.ServiceRuntimeException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorEditorServiceImpl implements AuthorEditorService {

    public static final String AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS = "Author id is incorrect! It should contains only digits!";
    public static final String THERE_ARE_NO_AUTHORS_WITH_ID = "There are no authors with id=";
    private final EvaluatingDataService evaluatingService;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Transactional(readOnly = true)
    @Override
    public Author getAuthorById(String id)  {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long authorId = Long.parseLong(id);

        Optional<Author> authorOptional = authorDao.getById(authorId);

        if (authorOptional.isEmpty())
            throw new ServiceRuntimeException(THERE_ARE_NO_AUTHORS_WITH_ID +id+"!");

        return authorOptional.get();

    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthorsById(String[] ids)  {

        evaluatingService.checkArrayOnDigitsThrowException("Author", ids);

        final List<Long> idsLong = Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());

        List<Author> result = authorDao.getById(idsLong);

        if (result.size() != ids.length){
            throw new ServiceRuntimeException("Results count doesn't match requested authors count:\n" +
                    "ids: "+List.of(ids)+"\n" +
                    "results: "+result.stream().map(author -> author.getId().toString()).collect(Collectors.joining(",")));
        }

        return result;
    }

    @Transactional
    @Override
    public int deleteAuthorById(String id) {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,AUTHOR_ID_IS_INCORRECT_IT_SHOULD_CONTAINS_ONLY_DIGITS);

        final long authorId = Long.parseLong(id);
        long authorsBookCount = bookDao.getBooksCountByAuthorId(authorId);

        if ( authorsBookCount > 0 )
            throw new ServiceRuntimeException("You can't delete author with id = "+id+"! " +
                "It's used in "+authorsBookCount+" books! " +
                "Try to delete books or change there's authors first");

        final int deletedRecCount = authorDao.delete(authorId);

        if (deletedRecCount == 0)
            throw new ServiceRuntimeException(THERE_ARE_NO_AUTHORS_WITH_ID+id);

        return deletedRecCount;
    }

    @Transactional
    @Override
    public Author addAuthor(Author author) {
        if (author == null) {
            throw new ServiceRuntimeException("Service error: author is null!");
        }

        evaluatingService.isTextNotNullAndNotBlank(author.getName(),"Author name should not be empty");

        return authorDao.insert(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

}
