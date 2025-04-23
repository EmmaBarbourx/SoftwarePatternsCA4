package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.admin.BookAdminOps;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.OrderItemRepository;
import com.example.softwarePatternsCA4.repository.ReviewRepository;
import com.example.softwarePatternsCA4.search.strategy.AuthorSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.BookSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.CategorySearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.PublisherSearchStrategy;
import com.example.softwarePatternsCA4.search.strategy.TitleSearchStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByAuthorStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByCategoryStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByPriceStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByPublisherStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortByTitleStrategy;
import com.example.softwarePatternsCA4.sort.strategy.SortStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookAdminOps {

	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
    private ReviewRepository reviewRepository;
	
	@Autowired 
	private OrderItemRepository  orderItemRepository;

    // Retrieve all books in the catalogue
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add a new book to the catalogue
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    
    // Get book by Id
    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    // Update an existing book
    public Book updateBook(int id, Book updatedBook) {
        return bookRepository.findById(id)
            .map(book -> {
            	

                if (updatedBook.getTitle()          != null) book.setTitle(updatedBook.getTitle());
                if (updatedBook.getAuthor()         != null) book.setAuthor(updatedBook.getAuthor());
                if (updatedBook.getPublisher()      != null) book.setPublisher(updatedBook.getPublisher());
                if (updatedBook.getPrice()          != 0.0) book.setPrice(updatedBook.getPrice());
                if (updatedBook.getCategory()       != null) book.setCategory(updatedBook.getCategory());
                if (updatedBook.getIsbnNumber()     != null) book.setIsbnNumber(updatedBook.getIsbnNumber());
                if (updatedBook.getAssociatedImage()!= null) book.setAssociatedImage(updatedBook.getAssociatedImage());

                // stockLevel can be 0, so only set when caller sends a non negative value
                if (updatedBook.getStockLevel() >= 0) book.setStockLevel(updatedBook.getStockLevel());
                
                return bookRepository.save(book);
            })
            .orElse(null);
    }

    // Delete a book from the catalogue
    public void deleteBook(int id) {
    	orderItemRepository.deleteByBookId(id);
    	reviewRepository.deleteByBookId(id);
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
        } else if ("category".equalsIgnoreCase(sortCriterion)) {
            strategy = new SortByCategoryStrategy();
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

    @Override
    public Book adjustStock(int id, int delta) {
        return bookRepository.findById(id)
                .map(b -> {
                    int newLevel = b.getStockLevel() + delta;
                    if (newLevel < 0) {
                        throw new IllegalArgumentException(
                            "Resulting stock would be negative (" + newLevel + ")");
                    }
                    b.setStockLevel(newLevel);
                    return bookRepository.save(b);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                    "Book not found: " + id));
    }
}