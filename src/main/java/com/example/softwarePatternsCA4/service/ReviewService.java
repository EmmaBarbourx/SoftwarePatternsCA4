package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.ObserverEvent.ReviewCreatedEvent;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.CustomerRepository;
import com.example.softwarePatternsCA4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired 
    private CustomerRepository customerRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired private ApplicationEventPublisher publisher;

    // Add a new review
    public Review addReview(Review review) {
    	 //  Validate rating
        Integer r = review.getRating();
        if (r != null && (r < 1 || r > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // 2) Fetch the real Customer and Book
        int customerId = review.getCustomer().getId();
        int bookId     = review.getBook().getId();

        Customer realCust = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("No customer " + customerId));
        Book     realBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("No book " + bookId));

        review.setCustomer(realCust);
        review.setBook(realBook);

        // Stamp date if needed
        if (review.getReviewDate() == null) {
            review.setReviewDate(LocalDateTime.now());
        }

        // Save & fire event
        Review saved = reviewRepository.save(review);
        publisher.publishEvent(new ReviewCreatedEvent(saved));
        return saved;
    }
    
    // Retrieve all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Retrieve a review by its ID
    public Optional<Review> getReviewById(int id) {
        return reviewRepository.findById(id);
    }

    // Update an existing review
    public Review updateReview(int id, Review updatedReview) {
        return reviewRepository.findById(id)
        		.map(r -> {
                    Integer newRating = updatedReview.getRating();
                    if (newRating != null && (newRating < 1 || newRating > 5)) {
                        throw new IllegalArgumentException("Rating must be between 1 and 5");
                    }
                    r.setRating(newRating);
                    r.setComment(updatedReview.getComment());
                    r.setReviewDate(
                      updatedReview.getReviewDate() != null
                        ? updatedReview.getReviewDate()
                        : LocalDateTime.now()
                    );
                    r.setCustomer(updatedReview.getCustomer());
                    r.setBook(updatedReview.getBook());
                    Review saved = reviewRepository.save(r);
                    // re-fire the event so average is updated on edits, too
                    publisher.publishEvent(new ReviewCreatedEvent(saved));
                    return saved;
                })
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + id));
        }

    // Delete a review
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}
