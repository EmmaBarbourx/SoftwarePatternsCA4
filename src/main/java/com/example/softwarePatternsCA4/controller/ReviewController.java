package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.entity.Review;
import com.example.softwarePatternsCA4.service.CustomerService;
import com.example.softwarePatternsCA4.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private 
    PasswordEncoder passwordEncoder;

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam(required = false) Integer bookId) {
    	List<Review> list = (bookId == null)
                ? reviewService.getAllReviews()
                : reviewService.getReviewsByBookId(bookId);

        return ResponseEntity.ok(list);
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
    public ResponseEntity<?> updateReview(@PathVariable int id, @RequestBody Review updatedReview, @RequestHeader("X-Username") String username,
            @RequestHeader("X-Password") String password) {
    	//  authenticate caller
        Optional<Customer> callerOpt = customerService.findByUsername(username);
        if (callerOpt.isEmpty() || !passwordEncoder.matches(password, callerOpt.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
        Customer caller = callerOpt.get();

        // load existing review
        Optional<Review> existingOpt = reviewService.getReviewById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Review existing = existingOpt.get();

        // enforce ownership
        if (existing.getCustomer().getId() != caller.getId()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You may only edit your own reviews");
        }

        // delegate to service
        Review saved = reviewService.updateReview(id, updatedReview);
        return ResponseEntity.ok(saved);
    }
    
    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteReview(@PathVariable int id, @RequestHeader("X-Username") String username,
            @RequestHeader("X-Password") String password) {
    	
    	// authenticate
        Optional<Customer> callerOpt = customerService.findByUsername(username);
        if (callerOpt.isEmpty() || !passwordEncoder.matches(password, callerOpt.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
        Customer caller = callerOpt.get();

        // load review
        Optional<Review> existingOpt = reviewService.getReviewById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Review existing = existingOpt.get();

        // ownership check
        if (existing.getCustomer().getId() != caller.getId()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You may only delete your own reviews");
        }
    	
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
