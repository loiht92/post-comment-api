package com.codegym.resfulapipostcomment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenerateService <T> {
    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Long id);

    void save(T model);

    void remove(Long id);

}
