package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    // Retrieve a shopping cart by its ID
    public Optional<ShoppingCart> getCartById(int id) {
        return shoppingCartRepository.findById(id);
    }

    // Update a shopping cart
    public ShoppingCart saveCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    // Delete a shopping cart
    public void deleteCart(int id) {
        shoppingCartRepository.deleteById(id);
    }
}
