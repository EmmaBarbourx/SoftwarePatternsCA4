package com.example.softwarePatternsCA4.ObserverEvent;

import com.example.softwarePatternsCA4.entity.Review;

public class ReviewCreatedEvent {
    private final Review review;
    public ReviewCreatedEvent(Review review) {
        this.review = review;
    }
    public Review getReview() {
        return review;
    }
}
