package com.example.softwarePatternsCA4.search.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.ArrayList;
import java.util.List;

public class CategorySearchStrategy implements BookSearchStrategy{
	
	@Override
    public List<Book> search(List<Book> books, String searchQuery) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory() != null &&
                book.getCategory().toLowerCase().contains(searchQuery.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
	
}
