package com.example.desktop_java_swing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


import java.io.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books", indexes = {
        @Index(name = "index_book_id", columnList = "book_id")
})
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, unique = true, columnDefinition = "bigint", name = "book_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String title;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String author;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    //Fiction and Non-fiction
    private String category;

    @Column(nullable = false)
    private int quantity;

}
