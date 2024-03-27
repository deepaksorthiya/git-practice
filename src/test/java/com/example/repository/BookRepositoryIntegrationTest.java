package com.example.repository;

import com.example.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        // Create test data
        Book book = new Book();
        book.setTitle("Test Book");


        // Save book to database
        Book savedBook = bookRepository.save(book);

        // Verify book is saved with auto-generated ID
        assertThat(savedBook.getId()).isNotNull();

        // Find book by ID
        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);

        // Verify book is found
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("Test Book");
    }

    // Add more integration test cases for other repository methods such as findById, findByTitle, etc.
}

