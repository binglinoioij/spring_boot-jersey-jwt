package com.example.repository;

import com.example.entity.mongo.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by binglin on 2016/9/15.
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findByName(String name, Pageable pageable);
}
