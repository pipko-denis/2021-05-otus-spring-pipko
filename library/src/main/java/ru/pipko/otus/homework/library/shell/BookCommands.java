package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.service.BookCommandsExecutorService;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookCommandsExecutorService commandsExecutorService;


    @ShellMethod(value = "Adding book with all requisites, split list of ids of authors and genres by comma", key = {"ba", "book-add"})
    public String addBook(@ShellOption(value = {"book", "b"}) String bookName,
                          @ShellOption(value = {"author", "a"}) String authors,
                          @ShellOption(value = {"genre", "g"}) String genreId) {

        return commandsExecutorService.addBook(bookName, authors, genreId);

    }

    @ShellMethod(value = "Adding book with all requisites", key = {"bd", "book-del"})
    public String deleteBook(@ShellOption(value = {"id"}) String bookId) {

        return commandsExecutorService.deleteBookById(bookId);

    }

    @ShellMethod(value = "Edit book's name, authors and genres, split list of authors and genres by comma", key = {"be", "book-edit"})
    public String editBook(@ShellOption(value = {"book-id", "id"}) String bookId,
                           @ShellOption(value = {"book", "b"}) String bookName,
                           @ShellOption(value = {"author", "a"}) String authorId,
                           @ShellOption(value = {"genre", "g"}) String genreId) {

        return commandsExecutorService.editBook(bookId, bookName, authorId, genreId);

    }

    @ShellMethod(value = "Listing all books", key = {"books-list", "bl"})
    public String getBooksList() {

        return commandsExecutorService.getAll();

    }


    @ShellMethod(value = "All information about book by it's id", key = {"book-info", "bi"})
    public String getBook(@ShellOption(value = {"id"}) String bookId) {

        return commandsExecutorService.getBookById(bookId);

    }

    @ShellMethod(value = "Get books list with theirs comments count, you can set list limit", key = {"book-comments-count", "bcc"})
    public String getBookComments(@ShellOption(value = {"limit","l"}, defaultValue = "5") Integer limit) {

        return commandsExecutorService.getBookCommentsCnt(limit.toString());

    }


}
