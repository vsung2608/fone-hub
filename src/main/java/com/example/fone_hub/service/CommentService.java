package com.example.fone_hub.service;

import com.example.fone_hub.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    // Find By UserId
    List<Comment> findByProductId(Long productId);

    // Add Comment
    void AddComment(Comment newComment, Long productId);
}
