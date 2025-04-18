package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Attributes 
    private Integer rating;  // Scale of 1–5
    private String comment;
    private LocalDateTime reviewDate;

    @ManyToOne
    private Customer customer; // Who submitted the review

    @ManyToOne
    private Book book; // Which book is being reviewed

    public Review() {
    }

    public Review(Integer rating, String comment, LocalDateTime reviewDate, Customer customer, Book book) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.customer = customer;
        this.book = book;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
               "rating=" + rating +
               ", comment='" + comment + '\'' +
               ", reviewDate=" + reviewDate +
               ", customer=" + (customer != null ? customer.getUsername() : "null") +
               ", book=" + (book != null ? book.getTitle() : "null") +
               '}';
    }
}