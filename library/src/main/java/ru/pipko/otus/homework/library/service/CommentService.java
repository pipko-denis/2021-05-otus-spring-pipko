package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Comment;

import java.util.List;

public interface CommentService {

    int deleteComment(long id);

    Comment addComment(long bookId, String commentText);

    List<Comment> getByBookId(String id);

}
