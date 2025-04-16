package com.example.softwarePatternsCA4.search.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.ArrayList;
import java.util.List;

public class AuthorSearchStrategy implements BookSearchStrategy{
	
	@Override
    public List<Book> search(List<Book> books, String searchQuery) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor() != null &&
                book.getAuthor().toLowerCase().contains(searchQuery.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

}
