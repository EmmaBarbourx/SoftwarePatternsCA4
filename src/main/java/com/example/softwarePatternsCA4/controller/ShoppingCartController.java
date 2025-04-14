package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Get a shopping cart by its id
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCartById(@PathVariable int id) {
        return shoppingCartService.getCartById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Update a shopping cart
    @PostMapping
    public ResponseEntity<ShoppingCart> saveCart(@RequestBody ShoppingCart cart) {
        ShoppingCart savedCart = shoppingCartService.saveCart(cart);
        return ResponseEntity.ok(savedCart);
    }
    
    // Delete a shopping cart
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable int id) {
        shoppingCartService.deleteCart(id);
        return ResponseEntity.ok("Shopping cart deleted successfully");
    }
}
