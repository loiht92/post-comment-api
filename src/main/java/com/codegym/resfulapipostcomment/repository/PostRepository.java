package com.codegym.resfulapipostcomment.repository;

import com.codegym.resfulapipostcomment.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
