package com.example.yunhoborad.comment.dto;

public record CommentRequest(
    Long BoardId,
    String content
) {
}
