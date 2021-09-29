package ru.pipko.otus.homework.library.shell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Comment;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.service.BooksEditorService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class BookCommands {

    private final BooksEditorService booksEditorService;

    private final BookDao bookDao;

    private final int outCommentsCount;

    public BookCommands(BooksEditorService booksEditorService, BookDao bookDao, @Value("${book.out.comments.count}") int outCommentsCount) {
        this.booksEditorService = booksEditorService;
        this.bookDao = bookDao;
        this.outCommentsCount = outCommentsCount;
    }


    @ShellMethod(value = "Adding book with all requisites, split list of authors and genres by comma", key = {"ba", "book-add"})
    public String addBook(@ShellOption(value = {"book", "b"}) String bookName,
                          @ShellOption(value = {"author", "a"}) String authors,
                          @ShellOption(value = {"genre", "g"}) String genreId) {

        Book book = booksEditorService.addBook(bookName, authors, genreId);

        return "Book added, id: " + book.getId() +
                ". Authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(",")) +
                ". Genres: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(","));
    }

    @ShellMethod(value = "Adding book with all requisites", key = {"bd", "book-del"})
    public String deleteBook(@ShellOption(value = {"id"}) String bookId) {

        int recCount = booksEditorService.deleteBookById(bookId);

        return "Book with id = " + bookId + " deleted from Books. Modified " + recCount + " rows.";
    }

    @ShellMethod(value = "Edit book's name, authors and genres, split list of authors and genres by comma", key = {"be", "book-edit"})
    public String editBook(@ShellOption(value = {"book-id", "id"}) String bookId,
                           @ShellOption(value = {"book", "b"}) String bookName,
                           @ShellOption(value = {"author", "a"}) String authorId,
                           @ShellOption(value = {"genre", "g"}) String genreId) {

        Book book = booksEditorService.editBook(bookId, bookName, authorId, genreId);

        return "Book with id = " + book.getId() + " modified.";
    }

    @Transactional
    @ShellMethod(value = "Listing all books", key = {"books-list", "bl"})
    public String getBooksList() {

        final List<Book> bookList = booksEditorService.getAllBooks();

        final String result = "Books list: \n" + bookList.stream().map(book ->
                "Book id:" + book.getId() + ": \"" + book.getName() + "\", " +
                        "authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(",")) + ", " +
                        "genre: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(",")) + ", " +
                        "comments: " + book.getComments().stream()
                        .map(comment -> getCommentPart(comment))
                        .limit(outCommentsCount)
                        .collect(Collectors.joining(",")) + " "
        ).collect(Collectors.joining("\n"));

        return result;
    }

    private String getCommentPart(Comment comment) {
        String commentText = comment.getText();
        if ((commentText != null) && (commentText.length() > 10)) {
            return commentText.substring(0, 10) + "...";
        } else {
            return commentText;
        }
    }


    @Transactional
    @ShellMethod(value = "All information about book", key = {"book-info", "bi"})
    public String getBook(@ShellOption(value = {"id"}) String bookId) {

        final Book book = booksEditorService.getBookById(bookId);

        final String result = "Book id: " + book.getId() + "\n" +
                "Book name: \"" + book.getName() + "\".\n" +
                "Authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")) + "\n" +
                "Genres: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")) + "\n" +
                "Comments: " + book.getComments().stream().map(Comment::getText).collect(Collectors.joining(", "));
        return result;
    }


}
