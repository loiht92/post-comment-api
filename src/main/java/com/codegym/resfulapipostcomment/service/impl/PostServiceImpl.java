package com.codegym.resfulapipostcomment.service.impl;

import com.codegym.resfulapipostcomment.model.Post;
import com.codegym.resfulapipostcomment.repository.PostRepository;
import com.codegym.resfulapipostcomment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void save(Post model) {
        postRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }
}
