package com.example.yunhoborad.board.ui;

import com.example.yunhoborad.board.application.BoardService;
import com.example.yunhoborad.board.dto.BoardRequest;
import com.example.yunhoborad.board.dto.BoardResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequest request) {
        Long boardId = boardService.create(request);
        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findBoard (@PathVariable Long id) {
        BoardResponse response = boardService.findBoard(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findBoards() {
        List<BoardResponse> responses = boardService.findBoards();
        return ResponseEntity.ok(responses);
    }
}
