package com.example.softwarePatternsCA4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.softwarePatternsCA4.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
	Optional<ShoppingCart> findByCustomerId(int customerId);
}
