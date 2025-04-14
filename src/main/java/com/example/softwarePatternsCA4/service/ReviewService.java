package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Add a new review
    public Review addReview(Review review) {
        return reviewRepository.save(review);
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
            .map(review -> {
                review.setRating(updatedReview.getRating());
                review.setComment(updatedReview.getComment());
                review.setReviewDate(updatedReview.getReviewDate());
                review.setCustomer(updatedReview.getCustomer());
                review.setBook(updatedReview.getBook());
                return reviewRepository.save(review);
            })
            .orElse(null);
    }

    // Delete a review
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}
