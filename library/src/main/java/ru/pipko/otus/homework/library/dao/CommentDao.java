package ru.pipko.otus.homework.library.dao;

import ru.pipko.otus.homework.library.domain.Comment;

import java.util.List;

public interface CommentDao {

    Comment insert(Comment comment);

    long delete(long id);

    List<Comment> getByBookId(long id);
}
