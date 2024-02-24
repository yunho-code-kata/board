package com.example.yunhoborad.board.dto;

import com.example.yunhoborad.board.domain.Board;

public record BoardReponse(
        Long id,
        String title,
        String content
) {

    public static BoardReponse from(Board board) {
        return new BoardReponse(
                board.getId(),
                board.getTitle(),
                board.getContent()
        );
    }
}
