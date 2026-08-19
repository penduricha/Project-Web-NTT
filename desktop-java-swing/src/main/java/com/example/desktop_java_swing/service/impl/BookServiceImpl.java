package com.example.desktop_java_swing.service.impl;

import com.example.desktop_java_swing.model.Book;
import com.example.desktop_java_swing.repository.BookRepository;
import com.example.desktop_java_swing.service.I_BookService;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements I_BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean addBook(Book book) throws JpaSystemException {
        Book bookFound = bookRepository.findBookByBookId(book.getBookId());
        if(bookFound == null) {
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBookByBookId(Long bookId) {
        Book bookFound = bookRepository.findBookByBookId(bookId);
        if(bookFound != null) {
            bookRepository.delete(bookFound);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() throws JpaSystemException {
        return bookRepository.findAll();
    }

    @Override
    public boolean updateBook(Book book) throws JpaSystemException {
        Book bookFound = bookRepository.findBookByBookId(book.getBookId());
        if(bookFound != null) {
            bookFound.setTitle(book.getTitle());
            bookFound.setAuthor(book.getAuthor());
            bookFound.setCategory(book.getCategory());
            bookFound.setQuantity(book.getQuantity());
            bookRepository.save(bookFound);
            return true;
        }
        return false;
    }

}
