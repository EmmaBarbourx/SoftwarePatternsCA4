package com.example.softwarePatternsCA4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import com.example.softwarePatternsCA4.entity.ShoppingCartItem;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
	
	Optional<ShoppingCartItem> findByShoppingCartAndBook(ShoppingCart cart, Book book);
  
}

