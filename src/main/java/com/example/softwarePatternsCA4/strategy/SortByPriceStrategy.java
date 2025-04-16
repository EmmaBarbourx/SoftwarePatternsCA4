package com.example.softwarePatternsCA4.strategy;

import com.example.softwarePatternsCA4.entity.Book;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByPriceStrategy implements SortStrategy{
	
	@Override
    public List<Book> sort(List<Book> books) {
        Collections.sort(books, Comparator.comparingDouble(Book::getPrice));
        return books;
    }

}
