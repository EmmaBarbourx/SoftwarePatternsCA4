package com.example.softwarePatternsCA4.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.softwarePatternsCA4.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
	@Modifying
    @Transactional
    @Query("DELETE FROM OrderItem oi WHERE oi.book.id = :bookId")
    void deleteByBookId(@Param("bookId") int bookId);
}
