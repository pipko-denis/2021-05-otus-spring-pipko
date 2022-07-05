package ru.pipko.otus.homework.library.service;

public interface BookCommandsExecutorService {

    String addBook(String bookName, String authorsInline, String genreIdsInline);

    String editBook(String bookId, String bookName, String authorsInline, String genreIdsInline) ;

    String getBookById(String id);

    String getBookCommentsCnt(String limit);

    String getAll();

    String deleteBookById(String id);

}
