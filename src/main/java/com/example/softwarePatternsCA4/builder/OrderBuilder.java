package com.example.softwarePatternsCA4.builder;

import com.example.softwarePatternsCA4.entity.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.*;

public class OrderBuilder {
    private Customer customer;
    private String shippingAddress;
    private List<ShoppingCartItem> cartItems = new ArrayList<>();

    private double subtotal;
    private double discount;       
    private double redemptionValue;
    private int pointsToRedeem;
    private int loyaltyPointsEarned;

    // 1)Attach customer
    public OrderBuilder forCustomer(Customer c) {
        this.customer = c;
        return this;
    }

    // 2) Attach shipping
    public OrderBuilder withShippingAddress(String addr) {
        this.shippingAddress = addr;
        return this;
    }

    // 3) Pull in cart items
    public OrderBuilder fromCartItems(List<ShoppingCartItem> items) {
        this.cartItems = items;
        return this;
    }

    // 4) Calculate subtotal
    public OrderBuilder calculateSubtotal() {
        subtotal = 0;
        for (var item : cartItems) {
            subtotal += item.getBook().getPrice() * item.getQuantity();
        }
        return this;
    }

    // 5) Apply discounts
    public OrderBuilder applyDiscount() {
        if (subtotal > 200)          discount = subtotal * 0.10;
        else if (subtotal > 100)     discount = subtotal * 0.05;
        else                         discount = 0;
        return this;
    }

    // 6) Loyalty points based on (subtotal – discount)
    public OrderBuilder accrueLoyaltyPoints() {
        double base = subtotal - discount;
        loyaltyPointsEarned = (int) floor(base / 10);
        return this;
    }

    // 7) Redeem existing points at €0.10 each, min 10, cap 50% of (subtotal – discount)
    public OrderBuilder redeemLoyaltyPoints(int requestedPoints) {
        double base = subtotal - discount;
        int available = customer.getLoyaltyPoints();

        // round request down to nearest 10
        int chunks = requestedPoints - (requestedPoints % 10);
        // cannot exceed customer's balance
        chunks = min(chunks, available);
        // cannot exceed 50% of base
        int maxChunks = (int) floor((base * 0.5) / 0.1);
        pointsToRedeem = min(chunks, maxChunks);
        redemptionValue = pointsToRedeem * 0.1;

        return this;
    }

    // 8) Build the final Order
    public Order build() {
        Order o = new Order();
        o.setCustomer(customer);
        o.setShippingAddress(shippingAddress);
        o.setOrderDate(LocalDateTime.now());
        o.setStatus("PENDING");

        // final total = (subtotal – discount) – redemptionValue
        double total = subtotal - discount - redemptionValue;
        o.setTotalCost(total);

        // attach order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (var sci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setBook(sci.getBook());
            oi.setQuantity(sci.getQuantity());
            oi.setUnitPrice(sci.getBook().getPrice());
            oi.setOrder(o);
            orderItems.add(oi);
        }
        o.setOrderItems(orderItems);

        // record discount and loyalty info
        o.setDiscount(discount + redemptionValue);
        o.setLoyaltyPointsEarned(loyaltyPointsEarned);
        o.setLoyaltyPointsRedeemed(pointsToRedeem);

        return o;
    }
}
