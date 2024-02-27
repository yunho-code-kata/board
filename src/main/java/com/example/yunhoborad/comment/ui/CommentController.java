package com.example.yunhoborad.comment.ui;

import com.example.yunhoborad.comment.application.CommentService;
import com.example.yunhoborad.comment.dto.CommentRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest request) {
        Long CommentId = commentService.createComment(request);
        return ResponseEntity.created(URI.create("/comments/" + CommentId)).build();}
}
