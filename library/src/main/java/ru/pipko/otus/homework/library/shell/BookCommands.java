package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.service.BooksEditorService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BooksEditorService booksEditorService;

    private final BookDao bookDao;

    @ShellMethod(value = "Adding book with all requisites", key = {"ba", "book-add"})
    public String addBook(@ShellOption(value = {"book", "b"} ) String bookName,
                          @ShellOption(value = {"author", "a"}) String authorId,
                          @ShellOption(value = {"genre", "g"}) String genreId) {

        Book book = booksEditorService.addBook(bookName,authorId,genreId);

        return "Book added, id: " + book.getId() + ". Genre id: " + book.getGenres().get(0).getId() + ". Author id: "+book.getAuthors().get(0).getId();
    }

    @ShellMethod(value = "Adding book with all requisites", key = {"bd", "book-del"})
    public String deleteBook(@ShellOption(value = {"id"} ) String bookId) {

        int recCount = booksEditorService.deleteBookById(bookId);

        return "Book with id = "+bookId+" deleted from Books. Modified "+recCount+" rows." ;
    }

    @ShellMethod(value = "Edit book's name, author and genre", key = {"be", "book-edit"})
    public String editBook(@ShellOption(value = {"book-id", "id"} ) String bookId,
                           @ShellOption(value = {"book", "b"} ) String bookName,
                           @ShellOption(value = {"author", "a"}) String authorId,
                           @ShellOption(value = {"genre", "g"}) String genreId) {

        Book book = booksEditorService.editBook(bookId,bookName,authorId,genreId);

        return  "Book with id = "+book.getId()+" modified."  ;
    }

    @Transactional
    @ShellMethod(value = "Listing all books", key = {"books-list", "bl"})
    public String getBooksList() {

        final List<Book> bookList = booksEditorService.getAllBooks();

        final String result = "Books list: \n" + bookList.stream().map(book ->
                "Book id="+book.getId()+": \"" + book.getName() + "\", " +
                "authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(",")) +", "+
                "genre: " + book.getGenres().get(0).getName() + " ")
                .collect(Collectors.joining("\n"));

        return result;
    }



}
