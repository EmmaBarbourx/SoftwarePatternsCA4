package com.example.softwarePatternsCA4.search.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.List;

public interface BookSearchStrategy {
	
	List<Book> search(List<Book> books, String searchQuery);

}
