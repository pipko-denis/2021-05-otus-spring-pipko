package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        Optional<Genre> optionalGenre = genreDao.getById(genreId);

        if (optionalGenre.isEmpty())
            throw new RuntimeException("There are no genres with id="+id+"!");

        return optionalGenre.get();
    }

    @Override
    public List<Genre> getGenresById(String[] idsInline) {
        evaluatingService.checkArrayOnDigitsThrowException("Genre", idsInline);

        final List<Long> idsLong = Arrays.stream(idsInline).map(Long::valueOf).collect(Collectors.toList());

        List<Genre> result = genreDao.getById(idsLong);

        if (result.size() != idsInline.length){
            throw new RuntimeException("Results count doesn't match requested authors count:\n" +
                    "ids: "+List.of(idsInline)+"\n" +
                    "results: "+result.stream().map(author -> author.getId().toString()).collect(Collectors.joining(",")));
        }

        return result;
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
