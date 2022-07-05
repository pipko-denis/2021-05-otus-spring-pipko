package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.exceptions.ServiceRuntimeException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreEditorServiceImpl implements GenreEditorService {

    public static final String GENRE_NAME_SHOULD_NOT_BE_EMPTY = "Genre name should not be empty";
    private final EvaluatingDataServiceImpl evaluatingService;
    private final GenreDao genreDao;

    @Transactional(readOnly = true)
    @Override
    public Genre getGenreById(String id)  {
        evaluatingService.throwExceptionIfNotOnlyDigitsInText(id,"Genre id is incorrect! It should contains only digits!");

        final long genreId = Long.parseLong(id);

        Optional<Genre> optionalGenre = genreDao.getById(genreId);

        if (optionalGenre.isEmpty())
            throw new ServiceRuntimeException("There are no genres with id="+id+"!");

        return optionalGenre.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getGenresById(String[] idsInline) {
        evaluatingService.checkArrayOnDigitsThrowException("Genre", idsInline);

        final List<Long> idsLong = Arrays.stream(idsInline).map(Long::valueOf).collect(Collectors.toList());

        List<Genre> result = genreDao.getById(idsLong);

        if (result.size() != idsInline.length){
            throw new ServiceRuntimeException("Results count doesn't match requested authors count:\n" +
                    "ids: "+List.of(idsInline)+"\n" +
                    "results: "+result.stream().map(author -> author.getId().toString()).collect(Collectors.joining(",")));
        }

        return result;
    }

    @Transactional
    @Override
    public Genre addGenre(Genre genre) {
        if (genre == null) {
            throw new ServiceRuntimeException("Service error: genre is null!");
        }
        evaluatingService.isTextNotNullAndNotBlank(genre.getName(), GENRE_NAME_SHOULD_NOT_BE_EMPTY);

        return genreDao.insert(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }


}
