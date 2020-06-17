package com.codegym.resfulapipostcomment.service;

import com.codegym.resfulapipostcomment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService extends GenerateService<Comment> {
    Page<Comment> findByPostId(Long id, Pageable pageable);

    Optional<Comment> findByIdAndPostId(Long id, Long post_id);
}
