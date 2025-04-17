package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.entity.ShoppingCartItem;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.CustomerRepository;
import com.example.softwarePatternsCA4.repository.ShoppingCartItemRepository;
import com.example.softwarePatternsCA4.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    // Retrieve a shopping cart by its ID
    public Optional<ShoppingCart> getCartById(int id) {
        return shoppingCartRepository.findById(id);
    }

    // Update a shopping cart
    public ShoppingCart saveCart(ShoppingCart cart) {
        // Load the Customer from the database
        Customer fullCustomer = customerRepository.findById(cart.getCustomer().getId())
            .orElseThrow(() -> new IllegalArgumentException(
                "No customer with id=" + cart.getCustomer().getId()));

        // Attach it to the cart
        cart.setCustomer(fullCustomer);

        //Save and return
        return shoppingCartRepository.save(cart);
    }

    // Delete a shopping cart
    public void deleteCart(int id) {
        shoppingCartRepository.deleteById(id);
    }
    
    // Add items to shopping cart
    public void addItemToCart(int cartId, int bookId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        // Check if item already exists in cart
        Optional<ShoppingCartItem> existingItem =
            shoppingCartItemRepository.findByShoppingCartAndBook(cart, book);

        if (existingItem.isPresent()) {
            ShoppingCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            shoppingCartItemRepository.save(item);
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(cart, book, quantity);
            shoppingCartItemRepository.save(newItem);
        }
    }
    
    // Remove item from shopping cart
    public void removeItemFromCart(int cartId, int bookId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        shoppingCartItemRepository
            .findByShoppingCartAndBook(cart, book)
            .ifPresent(shoppingCartItemRepository::delete);
    }
    
    // Update item in shopping cart
    public void updateItemQuantity(int cartId, int bookId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        ShoppingCartItem item = shoppingCartItemRepository
            .findByShoppingCartAndBook(cart, book)
            .orElseThrow(() -> new IllegalArgumentException(
                "Item not in cart: cartId=" + cartId + ", bookId=" + bookId));

        item.setQuantity(quantity);
        shoppingCartItemRepository.save(item);
    }
}
