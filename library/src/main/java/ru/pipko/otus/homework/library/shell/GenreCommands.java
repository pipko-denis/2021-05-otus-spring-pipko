package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.service.GenreEditorService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreEditorService genreService;

    @ShellMethod(value = "Adding genre command", key = {"ga", "genre-add"})
    public String addGenre(@ShellOption(value = {"name", "n"}) String name) {

        final Genre genre = new Genre(name);
        genreService.addGenre(genre);

        return "Genre added, new genre id is " + genre.getId() + ".";
    }

    @ShellMethod(value = "Listing all genres command", key = {"genre-list", "gl"})
    public String getGenresList() {

        final List<Genre> genresList = genreService.getAll();

        final String result = "Genres list: \n" + genresList.stream().map(genre ->
                "Genre: " + genre.getName())
                .collect(Collectors.joining("\n"));

        return result;
    }

}
