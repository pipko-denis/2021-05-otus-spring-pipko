package ru.pipko.otus.homework.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pipko.otus.homework.library.domain.Comment;
import ru.pipko.otus.homework.library.domain.Genre;
import ru.pipko.otus.homework.library.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    private final CommentService commentService;

    @ShellMethod(value = "Comments list by book id", key = {"comments-book","cb"})
    public String getCommentByBookId(@ShellOption(value = {"id", "b", "i"} ) String id){

        List<Comment> comments = commentService.getByBookId(id);

        return "Comments: \n"+ comments.stream().map(comment -> "id:  "+comment.getId() + ", text: " +comment.getText()).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Adding comment command", key = {"ca", "comment-add"})
    public String addComment(@ShellOption(value = {"bi", "b"}) String bookId, @ShellOption(value = {"text", "t"}) String text) {

        Comment comment = commentService.addComment(bookId, text);

        return "Comment added, new comment id is " + comment.getId() + ".";
    }

    @ShellMethod(value = "Deleting comment command", key = {"cd", "comment-del"})
    public String deleteComment(@ShellOption(value = {"id"}) String commentId) {

        commentService.deleteComment(commentId);

        return "Comment with id = "+commentId+" deleted from Comments." ;
    }


}
