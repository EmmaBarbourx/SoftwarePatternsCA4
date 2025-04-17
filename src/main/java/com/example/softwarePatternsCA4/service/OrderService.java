package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.builder.OrderBuilder;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.entity.OrderItem;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.entity.ShoppingCartItem;
import com.example.softwarePatternsCA4.repository.BookRepository;
import com.example.softwarePatternsCA4.repository.CustomerRepository;
import com.example.softwarePatternsCA4.repository.OrderRepository;
import com.example.softwarePatternsCA4.repository.ShoppingCartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
    private BookRepository bookRepository;

    // Create a new order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Retrieve an order by its ID
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    // Update an existing order
    public Order updateOrder(int id, Order updatedOrder) {
        return orderRepository.findById(id)
            .map(order -> {
                order.setOrderDate(updatedOrder.getOrderDate());
                order.setStatus(updatedOrder.getStatus());
                order.setShippingAddress(updatedOrder.getShippingAddress());
                order.setTotalCost(updatedOrder.getTotalCost());
                order.setCustomer(updatedOrder.getCustomer());
                return orderRepository.save(order);
            })
            .orElse(null);
    }

    // Delete an order
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
    
    public Order processCheckout(int cartId, int redeemPoints) {
        // Load cart and customer
        ShoppingCart cart = shoppingCartService.getCartById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        Customer cust = customerRepository.findById(cart.getCustomer().getId())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        // Load items
        List<ShoppingCartItem> items = 
            shoppingCartItemRepository.findByShoppingCartId(cartId);

        // Build the Order with OrderBuilder
        Order order = new OrderBuilder()
            .forCustomer(cust)
            .withShippingAddress(cust.getShippingAddress())
            .fromCartItems(items)
            .calculateSubtotal()
            .applyDiscount()
            .accrueLoyaltyPoints()
            .redeemLoyaltyPoints(redeemPoints)
            .build();

        // Save order
        Order saved = orderRepository.save(order);

        // Update stock
        for (OrderItem oi : saved.getOrderItems()) {
            Book b = oi.getBook();
            b.setStockLevel(b.getStockLevel() - oi.getQuantity());
            bookRepository.save(b);
        }
        // Update customer loyalty
        int newPoints = cust.getLoyaltyPoints()
                        - saved.getLoyaltyPointsRedeemed()
                        + saved.getLoyaltyPointsEarned();
        cust.setLoyaltyPoints(newPoints);
        customerRepository.save(cust);

        // Clear cart
        shoppingCartService.deleteCart(cartId);

        return saved;
    }
}

