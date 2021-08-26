package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.dao.AuthorJdbcDao;
import ru.pipko.otus.homework.library.dao.BookJdbcDao;
import ru.pipko.otus.homework.library.dao.GenreJdbcDao;
import ru.pipko.otus.homework.library.domain.Author;
import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.service.EvaluatingDataServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final EvaluatingDataServiceImpl evaluatingDataService;
    private final AuthorJdbcDao authorJdbcDao;
    private final GenreJdbcDao genreJdbcDao;
    private final BookJdbcDao bookJdbcDao;

    @ShellMethod(value = "Adding book with all requisites", key = {"a", "add"})
    public String addBook(@ShellOption(value = {"book","b"}) String bookInput,
                          @ShellOption(value = {"author","b"})  String authorInput,
                          @ShellOption(value = {"genre","g"})  String genreInput) {

        final Author author = new Author(authorInput);
        authorJdbcDao.insert(author);

        final Genre genre  = new Genre(genreInput);
        genreJdbcDao.insert(genre);

        final Book book  = new Book(bookInput,author,genre);
        bookJdbcDao.insert(book);

        return "Book added, id: " + book.getId()+". Genre inserted, id: "+book.getId()+". Author added, id: ";
    }

    @ShellMethod(value = "Listing all books", key = {"books-list","bl"})
    public String getBooksList() {

        final List<Book> bookList = bookJdbcDao.getAll();

        final String result = "Books list: " + bookList.stream().map(Book::getName).filter(s -> !s.isEmpty()).collect(Collectors.joining("\n"));

        return result;
    }




    private Author getAuthorIdByUserInput(String userInput) {
        Author result;
        if (evaluatingDataService.isThereAreOnlyDigitsInText(userInput)) {
            Integer authorId = Integer.valueOf(userInput);
            Optional<Author> authorOptional = authorJdbcDao.getById(authorId);
            if (authorOptional.isPresent()){
                result = authorOptional.get();
            } else{
                result = new Author(userInput);
                authorJdbcDao.insert(result);
            }
        } else {
            result = new Author(userInput);
        }
        return result;
    }

}
