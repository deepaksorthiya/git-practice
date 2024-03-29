package com.example.rest;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;
    private final WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping;
    private final Environment environment;

    public BookController(BookRepository bookRepository, Environment environment, WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping) {
        this.bookRepository = bookRepository;
        this.environment = environment;
        this.webMvcEndpointHandlerMapping = webMvcEndpointHandlerMapping;
    }

    @GetMapping("/envars")
    public ResponseEntity<String[]> getEnvs() {
        String[] profiles = this.environment.getActiveProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/envars1")
    public ResponseEntity<Map<RequestMappingInfo, HandlerMethod>> getEnvs1() {
        Map<RequestMappingInfo, HandlerMethod> methods = webMvcEndpointHandlerMapping.getHandlerMethods();
        return ResponseEntity.ok(methods);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}