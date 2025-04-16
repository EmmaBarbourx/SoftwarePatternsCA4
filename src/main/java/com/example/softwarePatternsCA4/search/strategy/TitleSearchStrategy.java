package com.example.softwarePatternsCA4.search.strategy;

import com.example.softwarePatternsCA4.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements BookSearchStrategy{
	
	@Override
    public List<Book> search(List<Book> books, String searchQuery) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle() != null &&
                book.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

}
