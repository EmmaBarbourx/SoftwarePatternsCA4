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
    
    // Add item to shopping cart
    @PostMapping("/{cartId}/items")
    public ResponseEntity<Void> addItemToCart(@PathVariable int cartId,@RequestParam int bookId,@RequestParam int quantity) {
        shoppingCartService.addItemToCart(cartId, bookId, quantity);
        return ResponseEntity.ok().build();
    }
    
    // Remove item from shopping cart
    @DeleteMapping("/{cartId}/items/{bookId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable int cartId,@PathVariable int bookId) {
        shoppingCartService.removeItemFromCart(cartId, bookId);
        return ResponseEntity.ok().build();
    }
    
    // Update quantity of item in shopping cart
    @PutMapping("/{cartId}/items/{bookId}")
    public ResponseEntity<Void> updateItemQuantity(@PathVariable int cartId,@PathVariable int bookId,@RequestParam int quantity) {
        shoppingCartService.updateItemQuantity(cartId, bookId, quantity);
        return ResponseEntity.ok().build();
    }
}
