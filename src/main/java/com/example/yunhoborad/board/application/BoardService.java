package com.example.yunhoborad.board.application;

import com.example.yunhoborad.board.domain.Board;
import com.example.yunhoborad.board.dto.BoardRequest;
import com.example.yunhoborad.board.dto.BoardResponse;
import com.example.yunhoborad.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
    public BoardResponse findBoard(Long id) {
        Board board = findBoardById(id);

        return BoardResponse.from(board);
    }

    public Board findBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id에 해당하는 게시글이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> findBoards() {
        List<BoardResponse> responses = boardRepository.findAll().stream()
                .map(BoardResponse::from)
                .toList();

        return responses;
    }
}
