package com.example.softwarePatternsCA4.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    //Each Customer has one ShoppingCart
    @OneToOne
    private Customer customer;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="shopping_cart_id")
    private List<ShoppingCartItem> items;


    public ShoppingCart() {
    }

    public ShoppingCart(Customer customer, List<ShoppingCartItem> items ) {
        this.customer = customer;
        this.items = items;
        
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
    public List<ShoppingCartItem> getItems() {
        return items;
    }
    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }
   

    @Override
    public String toString() {
        return "ShoppingCart{" +
               "id=" + id +
               ", customer=" + (customer != null ? customer.getUsername() : "null") +
               ", items=" + items +
               '}';
    }
}
