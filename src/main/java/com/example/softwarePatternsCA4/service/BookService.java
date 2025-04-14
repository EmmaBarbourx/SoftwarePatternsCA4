package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

	@Autowired
    private BookRepository bookRepository;

    // Retrieve all books in the catalogue
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add a new book to the catalogue
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Update an existing book
    public Book updateBook(int id, Book updatedBook) {
        return bookRepository.findById(id)
            .map(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setPublisher(updatedBook.getPublisher());
                book.setPrice(updatedBook.getPrice());
                book.setCategory(updatedBook.getCategory());
                book.setIsbnNumber(updatedBook.getIsbnNumber());
                book.setAssociatedImage(updatedBook.getAssociatedImage());
                book.setStockLevel(updatedBook.getStockLevel());
                return bookRepository.save(book);
            })
            .orElse(null);
    }

    // Delete a book from the catalogue
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}