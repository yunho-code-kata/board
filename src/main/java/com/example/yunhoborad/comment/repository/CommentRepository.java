package com.example.yunhoborad.comment.repository;


import com.example.yunhoborad.comment.domain.Comment;
import org.springframework.data.repository.Repository;

public interface CommentRepository extends Repository<Comment, Long> {

    Comment save(Comment comment);
}
