package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.exceptions.BookEditException;
import ru.pipko.otus.homework.library.service.BooksEditorHelper;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BooksEditorHelper booksEditorHelper;

    private final BookDao bookDao;

    @ShellMethod(value = "Adding book with all requisites", key = {"ba", "book-add"})
    public String addBook(@ShellOption(value = {"book", "b"} ) String bookInput,
                          @ShellOption(value = {"author", "b"}) String authorInput,
                          @ShellOption(value = {"genre", "g"}) String genreInput) throws BookEditException {

        Book book = booksEditorHelper.addBook(bookInput,authorInput,genreInput);

        return "Book added, id: " + book.getId() + ". Genre inserted, id: " + book.getGenre().getId() + ". Author added, id: "+book.getAuthor().getId();
    }

    @ShellMethod(value = "Adding book with all requisites", key = {"bd", "book-del"})
    public String deleteBook(@ShellOption(value = {"book", "b"} ) String bookInput)  throws BookEditException {

        long id = booksEditorHelper.deleteBookByDescription(bookInput);

        return "Book with id = "+id+" deleted from Books" ;
    }

    @ShellMethod(value = "Edit book's name, author and genre", key = {"be", "book-edit"})
    public String editBook(@ShellOption(value = {"book", "b"} ) String bookInput,
                           @ShellOption(value = {"author", "b"}) String authorInput,
                           @ShellOption(value = {"genre", "g"}) String genreInput) throws BookEditException {

        Book book = booksEditorHelper.editBook(bookInput,authorInput,genreInput);

        return  "Book with id = "+book.getId()+" modified."  ;
    }

    @ShellMethod(value = "Listing all books", key = {"books-list", "bl"})
    public String getBooksList() {

        final List<Book> bookList = bookDao.getAll();

        final String result = "Books list: \n" + bookList.stream().map(book ->
                "Book: \"" + book.getName() + "\", " +
                "author: " + book.getAuthor().getName() +", "+
                "genre: " + book.getGenre().getName() + " ")
                .collect(Collectors.joining("\n"));

        return result;
    }



}
