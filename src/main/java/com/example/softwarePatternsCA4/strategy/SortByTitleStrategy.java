package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByTitleStrategy implements SortStrategy{
	
	@Override
    public List<Book> sort(List<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return books;
    }

}
