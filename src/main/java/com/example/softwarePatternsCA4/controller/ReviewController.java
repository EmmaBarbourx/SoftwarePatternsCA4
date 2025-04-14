package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
    
    // Add a new review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }
    
    // Get a review by its id
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable int id) {
        Optional<Review> reviewOpt = reviewService.getReviewById(id);
        return reviewOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable int id, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok(updatedReview);
    }
    
    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
