package ru.pipko.otus.homework.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pipko.otus.homework.library.dao.AuthorDao;
import ru.pipko.otus.homework.library.dao.BookDao;
import ru.pipko.otus.homework.library.dao.GenreDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;

@Service
@RequiredArgsConstructor
public class BooksEditorHelperImpl implements BooksEditorHelper {


    private final EvaluatingDataServiceImpl evaluatingDataService;

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    @Override
    public Book addBook(String bookDescription, String authorDescription, String genreDescription) {
        final Author author = new Author(authorDescription);
        authorDao.insert(author);

        final Genre genre = new Genre(genreDescription);
        genreDao.insert(genre);

        final Book book = new Book(bookDescription, author, genre);
        bookDao.insert(book);

        return book;
    }

    @Override
    public Book editBook(String bookDescription, String authorDescription, String genreDescription) {
        return null;
    }

    @Override
    public Book getBookByDescription(String bookDescription) {
        return null;
    }

    @Override
    public long deleteBookByDescription(String bookDescription) {
        return 0;
    }
}
