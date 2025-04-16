package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.search.strategy.AuthorSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.BookSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.CategorySearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.PublisherSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.TitleSearchStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByAuthorStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByPriceStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByPublisherStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByTitleStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortStrategy;

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
    
    // To sort books
    public List<Book> getSortedBooks(String sortCriterion) {
        List<Book> books = getAllBooks();
        SortStrategy strategy;
        if ("price".equalsIgnoreCase(sortCriterion)) {
            strategy = new SortByPriceStrategy();
        } else if ("title".equalsIgnoreCase(sortCriterion)) {
            strategy = new SortByTitleStrategy();
        } else if ("author".equalsIgnoreCase(sortCriterion)) {
            strategy = new SortByAuthorStrategy();
        } else if ("publisher".equalsIgnoreCase(sortCriterion)) {
            strategy = new SortByPublisherStrategy();
        } else {
            // Default to sort by title
            strategy = new SortByTitleStrategy();
        }
        return strategy.sort(books);
    }
    
    // To search through books 
    public List<Book> searchBooks(String criterion, String searchQuery) {
        List<Book> books = getAllBooks();
        BookSearchStrategy strategy;

        if ("title".equalsIgnoreCase(criterion)) {
            strategy = new TitleSearchStrategy();
        } else if ("category".equalsIgnoreCase(criterion)) {
            strategy = new CategorySearchStrategy();
        } else if ("author".equalsIgnoreCase(criterion)) {
            strategy = new AuthorSearchStrategy();
        } else if ("publisher".equalsIgnoreCase(criterion)) {
            strategy = new PublisherSearchStrategy();
        } else {
            // Default to title search 
            strategy = new TitleSearchStrategy();
        }

        return strategy.search(books, searchQuery);
    }
}