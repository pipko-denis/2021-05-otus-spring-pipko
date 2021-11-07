package ru.pipko.otus.homework.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Comment;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.dto.BookComment;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCommandsExecutorServiceImpl implements BookCommandsExecutorService {

    private final BooksEditorService booksEditorService;

    private final int outCommentsCount;

    public BookCommandsExecutorServiceImpl(BooksEditorService booksEditorService,  @Value("${book.out.comments.count}") int outCommentsCount) {
        this.booksEditorService = booksEditorService;
        this.outCommentsCount = outCommentsCount;
    }


    @Override
    @Transactional
    public String addBook(String bookName, String authorsInline, String genreIdsInline) {

        final Book book = booksEditorService.addBook(bookName, authorsInline, genreIdsInline);

        return "Book added, id: " + book.getId() +
                ". Authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(",")) +
                ". Genres: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(","));
    }

    @Transactional
    @Override
    public String editBook(String bookId, String bookName, String authorsInline, String genreIdsInline) {

        Book book = booksEditorService.editBook(bookId, bookName, authorsInline, genreIdsInline);

        return "Book with id = " + book.getId() + " modified.";

    }

    @Transactional(readOnly = true)
    @Override
    public String getBookById(String bookId) {
        final Book book = booksEditorService.getBookById(bookId);

        return "Book id: " + book.getId() + "\n" +
                "Book name: \"" + book.getName() + "\".\n" +
                "Authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")) + "\n"
                +"Genres: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")) + "\n"
                +"Comments: " + book.getComments().stream().map(Comment::getText).collect(Collectors.joining(", "))
                ;
    }

    @Transactional(readOnly = true)
    @Override
    public String getBookCommentsCnt(String limit) {
        final List<BookComment> bookComments = booksEditorService.getBookCommentsCnt(limit);

        return bookComments.stream().map(book ->
                "Book name: \"" + book.getBookName() + ", comments count: " + book.getCommentsCount()).collect(Collectors.joining("\n"));
    }

    @Transactional(readOnly = true)
    @Override
    public String getAll() {

        final List<Book> bookList = booksEditorService.getAll();

        return "Books list: \n" + bookList.stream().map(book ->
                        "Book id:" + book.getId() + ": \"" + book.getName() + "\", " +
                                "authors: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(",")) + ", " +
                                "genres: " + book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(",")) + " "+
                                "comments: " + book.getComments().stream()
                                .map(comment -> getCommentPart(comment))
                                .limit(outCommentsCount)
                                .collect(Collectors.joining(",")) + " "
        ).collect(Collectors.joining("\n"));

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
    @Override
    public String deleteBookById(String bookId) {

        int recCount = booksEditorService.deleteBookById(bookId);

        return "Book with id = " + bookId + " deleted from Books. Modified " + recCount + " rows.";

    }
}
