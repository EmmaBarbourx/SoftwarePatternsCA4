package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.admin.AdminGuardProxy;
import com.example.softwarePatternsCA4.entity.Book;
import com.example.softwarePatternsCA4.service.CustomerService;
import com.example.softwarePatternsCA4.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final AdminGuardProxy proxy;
    
    private final CustomerService customerService;
    
    private final OrderService orderService;

    public AdminBookController(AdminGuardProxy proxy,CustomerService customerService, OrderService   orderService) {
        this.proxy = proxy;
        this.customerService = customerService; 
        this.orderService = orderService;
    }

    // Book endpoints
    
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Book b,@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
        proxy.verifyAdmin(u, p);              
        return ResponseEntity.ok(proxy.addBook(b));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody  Book b,@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
        proxy.verifyAdmin(u, p);
        return ResponseEntity.ok(proxy.updateBook(id, b));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id,@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
        proxy.verifyAdmin(u, p);
        proxy.deleteBook(id);
        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/{id}/stock")
    public ResponseEntity<?> adjustStock(@PathVariable int id, @RequestParam int delta,@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
        proxy.verifyAdmin(u, p);
        return ResponseEntity.ok(proxy.adjustStock(id, delta));
    }
    
    // Customer endpoints
    
    @GetMapping("/customers")
    public ResponseEntity<?> listCustomers(@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
        proxy.verifyAdmin(u, p);                   
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // purchase history 
    @GetMapping("/customers/{cid}/orders")
    public ResponseEntity<?> customerOrders(@PathVariable int cid,@RequestHeader("X-Username") String u,@RequestHeader("X-Password") String p) {
    proxy.verifyAdmin(u, p);
        // you already load orders by customer in OrderService:
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(cid));
    }
}
