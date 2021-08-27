package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.service.EvaluatingDataServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {


    private final EvaluatingDataServiceImpl evaluatingDataService;
    private final AuthorDao authorDao;

    @ShellMethod(value = "Adding author with all requisites", key = {"aa", "add-author"})
    public String addAuthor(@ShellOption(value = {"name", "n"}) String name) {

        final Author author = new Author(name);
        int recCount = authorDao.insert(author);

        return recCount+" records added into Author table, new author id is " + author.getId() + ".";
    }

    @ShellMethod(value = "Listing all authors", key = {"authors-list", "al"})
    public String getBooksList() {

        final List<Author> authorList = authorDao.getAll();

        final String result = "Authors list: \n" + authorList.stream().map(author ->
                        "Author: " + author.getName())
                .collect(Collectors.joining("\n"));

        return result;
    }

}
