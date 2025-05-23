package com.example.softwarePatternsCA4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.softwarePatternsCA4.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	 List<Order> findByCustomerId(int customerId);
}
