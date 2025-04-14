package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")  // "orders" avoids conflicts
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Attributes 
    private LocalDateTime orderDate;
    private String status;         // "pending" or "completed"
    private String shippingAddress;
    private double totalCost;

    // an order is associated with a Customer
    @ManyToOne
    private Customer customer;

    // One order can have many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(LocalDateTime orderDate, String status, String shippingAddress, double totalCost, Customer customer, List<OrderItem> orderItems) {
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.totalCost = totalCost;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
               "orderDate=" + orderDate +
               ", status='" + status + '\'' +
               ", shippingAddress='" + shippingAddress + '\'' +
               ", totalCost=" + totalCost +
               ", customer=" + (customer != null ? customer.getUsername() : "null") +
               '}';
    }
}