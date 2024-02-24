package com.example.yunhoborad.board.application;

import com.example.yunhoborad.board.domain.Board;
import com.example.yunhoborad.board.dto.BoardReponse;
import com.example.yunhoborad.board.dto.BoardRequest;
import com.example.yunhoborad.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long create(BoardRequest request) {
        Board board = boardRepository.save(
                Board.builder()
                        .title(request.title())
                        .content(request.content())
                        .build()
        );

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardReponse findBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        return BoardReponse.from(board);
    }
}
