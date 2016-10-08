package com.example.service.impl;

import com.example.entity.mongo.Book;
import com.example.exception.ServiceException;
import com.example.repository.BookRepository;
import com.example.service.BookService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by binglin on 2016/9/15.
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Inject
    private BookRepository bookRepository;

    @Override
    public Book get(Long id) {
        Book book = bookRepository.findOne(id);
        if (null == book) {
            throw new ServiceException(30001, "not found");
        }
        return book;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll(new PageRequest(0, 10)).getContent();
    }

    @Override
    public Page<Book> findByName(String name, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Book> byName = bookRepository.findByName(name, pageRequest);
        return byName;
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.delete(id.longValue());
    }
}
