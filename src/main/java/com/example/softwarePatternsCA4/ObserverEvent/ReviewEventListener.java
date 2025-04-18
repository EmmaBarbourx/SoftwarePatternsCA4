package com.example.softwarePatternsCA4.ObserverEvent;

import com.example.softwarePatternsCA4.ObserverEvent.ReviewCreatedEvent;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewEventListener {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BookRepository bookRepository;

    @EventListener
    public void onReviewCreated(ReviewCreatedEvent ev) {
        Review review = ev.getReview();
        Book book = review.getBook();

        // recalc average over all reviews for that book 
        List<Review> reviews = reviewRepository.findByBook(book);
        int sum = 0, count = 0;
        for (Review r : reviews) {
            Integer rating = r.getRating();
            if (rating != null) {
                sum += rating;
                count++;
            }
        }
        double avg = (count > 0) ? (double) sum / count : 0.0;

        // persist it
        book.setAverageRating(avg);
        bookRepository.save(book);
    }
}
