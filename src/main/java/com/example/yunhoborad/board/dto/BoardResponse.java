package com.example.yunhoborad.board.dto;

import com.example.yunhoborad.board.domain.Board;

public record BoardResponse(
        Long id,
        String title,
        String content
) {

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent()
        );
    }
}
