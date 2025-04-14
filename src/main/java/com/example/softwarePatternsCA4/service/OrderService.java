package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OrderService {
	
	@Autowired
    private OrderRepository orderRepository;

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
}

