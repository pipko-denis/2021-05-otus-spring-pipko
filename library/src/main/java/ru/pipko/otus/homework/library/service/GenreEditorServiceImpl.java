package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreEditorServiceImpl implements GenreEditorService {

    private final EvaluatingDataServiceImpl evaluatingService;
    private final GenreDao genreDao;

    @Transactional
    @Override
    public Genre getGenreById(String id)  {
        if ( ! evaluatingService.isThereAreOnlyDigitsInText(id) )
            throw new RuntimeException("Genre id is incorrect! It should contains only digits!");

        final long genreId = Long.parseLong(id);
        try {
            return genreDao.getById(genreId);
        } catch (IncorrectResultSizeDataAccessException ex){
            throw new RuntimeException("There are no genres with id="+id+"!");
        }
    }

    @Override
    public List<Genre> getGenresById(String[] idsInline) {
        evaluatingService.checkArrayOnDigitsThrowException("Genre", idsInline);

        final List<Long> idsLong = Arrays.stream(idsInline).map(id -> Long.valueOf(id)).collect(Collectors.toList());

        return genreDao.getById(idsLong);
    }

    @Override
    public Genre addGenre(Genre genre) {
        if (genre == null) {
            throw new RuntimeException("Service error: genre is null!");
        }
        if ( ! evaluatingService.isTextNotNullAndNotBlank(genre.getName()) )
            throw new RuntimeException("Genre name should not be empty");

        return genreDao.insert(genre);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }


}
