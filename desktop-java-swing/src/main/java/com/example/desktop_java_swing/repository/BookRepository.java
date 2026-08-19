package com.example.desktop_java_swing.repository;

import com.example.desktop_java_swing.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookByBookId(Long bookId);

}
