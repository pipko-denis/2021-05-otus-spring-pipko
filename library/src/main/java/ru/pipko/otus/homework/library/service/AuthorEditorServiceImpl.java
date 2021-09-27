package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorEditorServiceImpl implements AuthorEditorService {

    private final EvaluatingDataService evaluatingService;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Override
    public Author getAuthorById(String id)  {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException("Author id is incorrect! It should contains only digits!");

        final long authorId = Long.parseLong(id);
        try {
            return authorDao.getById(authorId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("There are no authors with id="+id+"!");
        }
    }

    private void checkArrayOnDigitsThrowException(String[] ids){
        final List<String> idsNotDigits = Arrays.stream(ids).filter(id -> !evaluatingService.isThereAreOnlyDigitsInText(id)).collect(Collectors.toList());

        if (! idsNotDigits.isEmpty()){
            throw new RuntimeException("Author id is incorrect! It should contains only digits! Wrong ids: "+String.join(",",idsNotDigits));
        }
    }

    @Override
    public List<Author> getAuthorsById(String[] ids)  {

        checkArrayOnDigitsThrowException(ids);

        final List<Long> idsLong = Arrays.stream(ids).map(id -> Long.valueOf(id)).collect(Collectors.toList());

        return authorDao.getById(idsLong);
    }

    @Override
    public int deleteAuthorById(String id) {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException("Author id is incorrect! It should contains only digits!");

        final long authorId = Long.parseLong(id);
        long authorsBookCount = bookDao.getBooksCountByAuthorId(authorId);

        if ( authorsBookCount > 0 )
            throw new RuntimeException("You can't delete author with id = "+id+"! " +
                "It's used in "+authorsBookCount+" books! " +
                "Try to delete books or change there's authors first");

        final int deletedRecCount = authorDao.delete(authorId);

        if (deletedRecCount == 0)
            throw new RuntimeException("There are no authors with id="+id);

        return deletedRecCount;
    }

    @Override
    public Author insert(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

}
