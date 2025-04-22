package com.example.softwarePatternsCA4.admin;

import com.example.softwarePatternsCA4.entity.Book;

public interface BookAdminOps {

    Book addBook   (Book b);                 // create
    Book updateBook(int id, Book b);         // update
    void deleteBook(int id);                 // delete
    Book adjustStock(int id, int delta);     // +/- stock   
}