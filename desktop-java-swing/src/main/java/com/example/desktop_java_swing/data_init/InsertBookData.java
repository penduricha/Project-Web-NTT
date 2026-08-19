package com.example.desktop_java_swing.data_init;

import com.example.desktop_java_swing.model.Book;
import com.example.desktop_java_swing.repository.BookRepository;
//import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InsertBookData {

    private final JdbcTemplate jdbcTemplate;

    private final BookRepository bookRepository;

    public InsertBookData(JdbcTemplate jdbcTemplate, BookRepository bookRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() throws JpaSystemException {
        List<String> codeSQLSetUtf8List = List.of(
                "ALTER TABLE books CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
        );

        for (String sqlStatement : codeSQLSetUtf8List) {
            try {
                jdbcTemplate.execute(sqlStatement);
                //System.out.println("Executed: " + sqlStatement); // Log success
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        try {
            Integer bookCount = jdbcTemplate.queryForObject("select count(*) from books", Integer.class);
            if(bookCount == 0) {

                List<Book> bookList = getBookList();
                bookRepository.saveAll(bookList);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();

        Book book1 = new Book();
        book1.setBookId(1001L);
        book1.setTitle("To Kill a Mockingbird");
        book1.setAuthor("Harper Lee");
        book1.setCategory("Fiction");
        book1.setQuantity(50);
        bookList.add(book1);

        Book book2 = new Book();
        book2.setBookId(1002L);
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setCategory("Fiction");
        book2.setQuantity(75);
        bookList.add(book2);

        Book book3 = new Book();
        book3.setBookId(1003L);
        book3.setTitle("Sapiens: A Brief History of Humankind");
        book3.setAuthor("Yuval Noah Harari");
        book3.setCategory("Non-fiction");
        book3.setQuantity(120);
        bookList.add(book3);

        Book book4 = new Book();
        book4.setBookId(1004L);
        book4.setTitle("Educated");
        book4.setAuthor("Tara Westover");
        book4.setCategory("Non-fiction");
        book4.setQuantity(90);
        bookList.add(book4);

        Book book5 = new Book();
        book5.setBookId(1005L);
        book5.setTitle("Good Omens");
        book5.setAuthor("Terry Pratchett & Neil Gaiman");
        book5.setCategory("Comedy");
        book5.setQuantity(40);
        bookList.add(book5);

        Book book6 = new Book();
        book6.setBookId(1006L);
        book6.setTitle("The Hitchhiker's Guide to the Galaxy");
        book6.setAuthor("Douglas Adams");
        book6.setCategory("Comedy");
        book6.setQuantity(60);
        bookList.add(book6);

        Book book7 = new Book();
        book7.setBookId(1007L);
        book7.setTitle("The Great Gatsby");
        book7.setAuthor("F. Scott Fitzgerald");
        book7.setCategory("Fiction");
        book7.setQuantity(85);
        bookList.add(book7);

        Book book8 = new Book();
        book8.setBookId(1008L);
        book8.setTitle("Atomic Habits");
        book8.setAuthor("James Clear");
        book8.setCategory("Non-fiction");
        book8.setQuantity(150);
        bookList.add(book8);

        Book book9 = new Book();
        book9.setBookId(1009L);
        book9.setTitle("Bossypants");
        book9.setAuthor("Tina Fey");
        book9.setCategory("Comedy");
        book9.setQuantity(30);
        bookList.add(book9);

        Book book10 = new Book();
        book10.setBookId(1010L);
        book10.setTitle("Pride and Prejudice");
        book10.setAuthor("Jane Austen");
        book10.setCategory("Fiction");
        book10.setQuantity(110);
        bookList.add(book10);

        return bookList;
    }
}
