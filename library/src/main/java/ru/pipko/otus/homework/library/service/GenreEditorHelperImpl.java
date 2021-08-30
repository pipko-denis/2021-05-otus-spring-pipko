package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreEditorHelperImpl implements GenreEditorHelper{

    private final EvaluatingDataServiceImpl evaluatingService;
    private final GenreDao genreDao;

    @Override
    public Genre getGenreById(String id)  {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) ) throw new RuntimeException("Genre id is incorrect! It should contains only digits!");
        final Long genreId = Long.valueOf(id);
        try {
            return genreDao.getById(genreId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("There are no genres with id="+id+"!");
        }
    }


}
