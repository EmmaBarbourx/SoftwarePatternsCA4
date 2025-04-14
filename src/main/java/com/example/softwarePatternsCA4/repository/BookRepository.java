package com.example.softwarePatternsCA4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.softwarePatternsCA4.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
