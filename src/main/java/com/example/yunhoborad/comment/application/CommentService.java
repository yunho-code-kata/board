package com.example.yunhoborad.comment.application;

import com.example.yunhoborad.board.application.BoardService;
import com.example.yunhoborad.board.domain.Board;
import com.example.yunhoborad.comment.domain.Comment;
import com.example.yunhoborad.comment.dto.CommentRequest;
import com.example.yunhoborad.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final BoardService boardService;
    private final CommentRepository commentRepository;

    public CommentService(
            BoardService boardService,
            CommentRepository commentRepository
    ) {
        this.boardService = boardService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long createComment(CommentRequest request) {
        Board board = boardService.findBoardById(request.BoardId());

        Comment comment = commentRepository.save(
                Comment.builder()
                        .content(request.content())
                        .board(board)
                        .build()
        );

        return comment.getId();
    }
}
