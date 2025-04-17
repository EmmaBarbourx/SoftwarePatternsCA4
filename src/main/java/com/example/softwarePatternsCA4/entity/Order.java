package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")  // "orders" avoids conflicts
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Attributes 
    private LocalDateTime orderDate;
    private String status;         // "pending" or "completed"
    private String shippingAddress;
    private double totalCost;
    private double discount;              
    private int loyaltyPointsEarned;       
    private int loyaltyPointsRedeemed; 

    // an order is associated with a Customer
    @ManyToOne
    private Customer customer;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(LocalDateTime orderDate, String status, String shippingAddress, double totalCost, Customer customer,List<OrderItem> orderItems,
            double discount,int loyaltyPointsEarned, int loyaltyPointsRedeemed) {
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.totalCost = totalCost;
        this.customer = customer;
        this.orderItems = orderItems;
        this.discount = discount;
        this.loyaltyPointsEarned = loyaltyPointsEarned;
        this.loyaltyPointsRedeemed = loyaltyPointsRedeemed;
        
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

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getLoyaltyPointsEarned() {
        return loyaltyPointsEarned;
    }
    public void setLoyaltyPointsEarned(int loyaltyPointsEarned) {
        this.loyaltyPointsEarned = loyaltyPointsEarned;
    }

    public int getLoyaltyPointsRedeemed() {
        return loyaltyPointsRedeemed;
    }
    public void setLoyaltyPointsRedeemed(int loyaltyPointsRedeemed) {
        this.loyaltyPointsRedeemed = loyaltyPointsRedeemed;
    }
   

    @Override
    public String toString() {
        return "Order{" +
               "orderDate=" + orderDate +
               ", status='" + status + '\'' +
               ", shippingAddress='" + shippingAddress + '\'' +
               ", totalCost=" + totalCost +
                ", discount=" + discount +
               ", loyaltyPointsEarned=" + loyaltyPointsEarned +
               ", loyaltyPointsRedeemed=" + loyaltyPointsRedeemed +
               ", customer=" + (customer != null ? customer.getUsername() : "null") +
               '}';
    }
}