package com.example.softwarePatternsCA4.entity;

import jakarta.persistence.*;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    //Each Customer has one ShoppingCart
    @OneToOne
    private Customer customer;


    public ShoppingCart() {
    }

    public ShoppingCart(Customer customer ) {
        this.customer = customer;
        
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
   

    @Override
    public String toString() {
        return "ShoppingCart{" +
               "id=" + id +
               ", customer=" + (customer != null ? customer.getUsername() : "null") +
               '}';
    }
}
