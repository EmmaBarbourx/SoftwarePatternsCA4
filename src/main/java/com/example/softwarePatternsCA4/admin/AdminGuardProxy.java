package com.example.softwarePatternsCA4.admin;

import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.service.BookService;
import com.example.softwarePatternsCA4.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component         
public class AdminGuardProxy implements BookAdminOps {

    private final BookService     delegate;
    private final CustomerService customerSvc;
    private final PasswordEncoder encoder;

    public AdminGuardProxy(BookService delegate,CustomerService customerSvc,PasswordEncoder encoder) {
        this.delegate    = delegate;
        this.customerSvc = customerSvc;
        this.encoder     = encoder;
    }

    
    public void verifyAdmin(String user, String pwd) {
        Customer c = customerSvc.findByUsername(user)
                                .orElseThrow(() ->
                                     new SecurityException("Unknown user"));
        if (!"ADMIN".equalsIgnoreCase(c.getUserRole()))
            throw new SecurityException("Admin role required");
        if (!encoder.matches(pwd, c.getPassword()))
            throw new SecurityException("Bad credentials");
    }

    @Override
    public Book addBook(Book b) {   // called from controller
        return delegate.addBook(b);
    }
    @Override
    public Book updateBook(int id, Book b) {
        return delegate.updateBook(id, b);
    }
    @Override
    public void deleteBook(int id) {
        delegate.deleteBook(id);
    }
    @Override
    public Book adjustStock(int id, int delta) {
        Book book = delegate.getBookById(id)
                            .orElseThrow(() ->
                                 new IllegalArgumentException("Book not found "+id));
        book.setStockLevel(book.getStockLevel() + delta);
        return delegate.updateBook(id, book);
    }
}