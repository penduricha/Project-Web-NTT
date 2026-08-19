package com.example.desktop_java_swing.service;

import com.example.desktop_java_swing.model.Book;

import java.util.List;

public interface I_BookService {

    public boolean addBook(Book book);

    public boolean deleteBookByBookId(Long bookId);

    public List<Book> getAllBooks();

    public boolean updateBook(Book book);
}
