package com.codegym.resfulapipostcomment.service.impl;

import com.codegym.resfulapipostcomment.model.Comment;
import com.codegym.resfulapipostcomment.repository.CommentRepository;
import com.codegym.resfulapipostcomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> findByPostId(Long id, Pageable pageable) {
        return commentRepository.findByPostId(id, pageable);
    }

    @Override
    public Optional<Comment> findByIdAndPostId(Long id, Long post_id) {
        return commentRepository.findByIdAndPostId(id, post_id);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void save(Comment model) {
        commentRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }


}
