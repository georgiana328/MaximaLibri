package com.maximaLibri.maximaLibriV2.service;

import com.maximaLibri.maximaLibriV2.model.Book;
import com.maximaLibri.maximaLibriV2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getTop10() {
        //return bookRepository.getTop10();
        return bookRepository.findAll();
    }
}
