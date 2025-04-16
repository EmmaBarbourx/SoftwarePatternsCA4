package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

 
    private String username;
    private String password;       // Need to secure with spring security
    private String shippingAddress;
    private String paymentMethod; 
    private String emailAddress;
    private String userRole;       // customer or admin

    public Customer() {
    }

    public Customer(String username, String password, String shippingAddress, String paymentMethod, String emailAddress, String userRole) {
        this.username = username;
        this.password = password;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.emailAddress = emailAddress;
        this.userRole = userRole;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Customer{" +
               "username='" + username + '\'' +
               ", shippingAddress='" + shippingAddress + '\'' +
               ", paymentMethod='" + paymentMethod + '\'' +
               ", emailAddress='" + emailAddress + '\'' +
               ", userRole='" + userRole + '\'' +
               '}';
    }
}
