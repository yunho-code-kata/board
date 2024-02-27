package com.example.yunhoborad.comment.repository;


import com.example.yunhoborad.board.domain.Board;
import com.example.yunhoborad.comment.domain.Comment;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface CommentRepository extends Repository<Comment, Long> {

    Comment save(Comment comment);

    List<Comment> findByBoard(Board board);

    List<Comment> findByBoardId(Long id);
}
