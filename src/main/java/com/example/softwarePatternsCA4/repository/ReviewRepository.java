package com.example.softwarePatternsCA4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByBook(Book book);
}
