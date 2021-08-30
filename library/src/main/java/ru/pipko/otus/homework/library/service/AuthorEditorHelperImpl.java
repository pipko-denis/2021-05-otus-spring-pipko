package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorEditorHelperImpl implements AuthorEditorHelper {

    private final EvaluatingDataServiceImpl evaluatingService;
    private final AuthorDao authorDao;

    @Override
    public Author getAuthorById(String id)  {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) ) throw new RuntimeException("Author id is incorrect! It should contains only digits!");
        final Long authorId = Long.valueOf(id);
        try {
            return authorDao.getById(authorId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("There are no authors with id="+id+"!");
        }
    }

}
