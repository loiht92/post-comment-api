package com.codegym.resfulapipostcomment.repository;

import com.codegym.resfulapipostcomment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByPostId(Long id, Pageable pageable);

    Optional<Comment> findByIdAndPostId(Long id, Long post_id);
}
