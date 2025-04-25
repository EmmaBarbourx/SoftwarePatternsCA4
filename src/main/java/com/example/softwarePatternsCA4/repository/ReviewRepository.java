package com.example.softwarePatternsCA4.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByBook(Book book);
	
	@Modifying
    @Transactional         
    @Query("DELETE FROM Review r WHERE r.book.id = :bookId")
    void deleteByBookId(@Param("bookId") int bookId);
	
	List<Review> findByBookId(int bookId);
}
