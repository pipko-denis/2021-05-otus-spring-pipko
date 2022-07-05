package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.service.AuthorEditorService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorEditorService authorService;

    @ShellMethod(value = "Adding author command", key = {"aa", "author-add"})
    public String addAuthor(@ShellOption(value = {"name", "n"}) String name) {
        final Author author = authorService.addAuthor(new Author(name));

        return "Author added into to database, new author id is " + author.getId() + ".";
    }

    @ShellMethod(value = "Listing all authors command", key = {"authors-list", "al"})
    public String getAuthorsList() {

        final List<Author> authorList = authorService.getAll();

        final String result = "Authors list: \n" + authorList.stream().map(author ->
                        "Author "+author.getId()+": " + author.getName())
                .collect(Collectors.joining("\n"));

        return result;
    }

    @ShellMethod(value = "Deleting author command", key = {"ad", "author-del"})
    public String deleteAuthor(@ShellOption(value = {"id"}) String authorId) {

        int recCount = authorService.deleteAuthorById(authorId);

        return "Author with id = "+authorId+" deleted from Authors. Modified "+recCount+" rows." ;
    }

}
