package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.List;

public interface SortStrategy {
	
	List<Book> sort(List<Book> books);
}
