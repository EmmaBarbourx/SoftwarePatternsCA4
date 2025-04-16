package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    
    // Sort books by sorting criteria
    @GetMapping("/sort")
    public ResponseEntity<List<Book>> getSortedBooks(@RequestParam("criterion") String criterion) {
        List<Book> sortedBooks = bookService.getSortedBooks(criterion);
        return ResponseEntity.ok(sortedBooks);
    }
    
    // Search books by criteria and query
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("criterion") String criterion,@RequestParam("query") String query) {
        List<Book> results = bookService.searchBooks(criterion, query);
        return ResponseEntity.ok(results);
    }
    
    // Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }
    
    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }
    
    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
