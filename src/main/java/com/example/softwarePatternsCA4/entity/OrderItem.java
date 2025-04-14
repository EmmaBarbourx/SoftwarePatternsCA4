package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    // Each OrderItem is linked to an Order
    @ManyToOne
    private Order order;  

    // Reference to the Book being purchased
    @ManyToOne
    private Book book;    

    // Attributes
    private int quantity;
    private double unitPrice;  // price at the time of order

    public OrderItem() {
    }

    public OrderItem(Order order, Book book, int quantity, double unitPrice) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "book=" + (book != null ? book.getTitle() : "null") +
               ", quantity=" + quantity +
               ", unitPrice=" + unitPrice +
               '}';
    }
}
