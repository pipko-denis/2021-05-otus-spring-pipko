package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreDao genreDao;

    @ShellMethod(value = "Adding genre command", key = {"ga", "genre-add"})
    public String addGenre(@ShellOption(value = {"name", "n"}) String name) {

        final Genre genre = new Genre(name);
        int recCount = genreDao.insert(genre);

        return recCount+" records added into Genre table, new genre id is " + genre.getId() + ".";
    }

    @ShellMethod(value = "Listing all genres command", key = {"genre-list", "gl"})
    public String getGenresList() {

        final List<Genre> genresList = genreDao.getAll();

        final String result = "Genres list: \n" + genresList.stream().map(genre ->
                "Genre: " + genre.getName())
                .collect(Collectors.joining("\n"));

        return result;
    }

}
