package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Comment;

import java.util.List;

public interface CommentService {

    public void deleteComment(String id);

    Comment addComment(String bookId, String commentText);

    List<Comment> getByBookId(String id);

}
