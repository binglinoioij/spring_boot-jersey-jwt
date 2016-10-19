package com.example.service;

import com.example.entity.mongo.Book;
import com.example.exception.ServiceException;

import org.springframework.data.domain.Page;

/**
 * Created by binglin on 2016/9/15.
 */
public interface BookService {
    Book get(Long id) throws ServiceException;

    void save(Book book);

    Page<Book> listAll();

    Page<Book> findByName(String name, Integer page, Integer size);

    void deleteById(Integer id);
}
