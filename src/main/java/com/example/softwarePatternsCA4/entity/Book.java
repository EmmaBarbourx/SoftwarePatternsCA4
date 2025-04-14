package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Attributes 
    private String title;
    private String author;         
    private String publisher;   
    private double price;
    private String category;
    private String isbnNumber;
    private String associatedImage;
    private int stockLevel;        // To manage inventory

    public Book() {
    }

    public Book(String title, String author, String publisher, double price, String category, String isbnNumber, String associatedImage, int stockLevel) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.category = category;
        this.isbnNumber = isbnNumber;
        this.associatedImage = associatedImage;
        this.stockLevel = stockLevel;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getIsbnNumber() {
        return isbnNumber;
    }
    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }
    public String getAssociatedImage() {
        return associatedImage;
    }
    public void setAssociatedImage(String associatedImage) {
        this.associatedImage = associatedImage;
    }
    public int getStockLevel() {
        return stockLevel;
    }
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    @Override
    public String toString() {
        return "Book{" +
               "title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", publisher='" + publisher + '\'' +
               ", price=" + price +
               ", category='" + category + '\'' +
               ", isbnNumber='" + isbnNumber + '\'' +
               ", associatedImage='" + associatedImage + '\'' +
               ", stockLevel=" + stockLevel +
               '}';
    }
}
