package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.service.CustomerService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired 
    private PasswordEncoder passwordEncoder; 

    // Register a new customer
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(registeredCustomer);
    }

    // Get customer by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        return customerService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a customer by id
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    // Delete a customer by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {

        String user = body.get("username");
        String pass = body.get("password");

        if (user == null || pass == null) {
            return ResponseEntity.status(400).body("username & password required");
        }

        return customerService.findByUsername(user)
            .map(cust -> {
                if (passwordEncoder.matches(pass, cust.getPassword())) {
                    return ResponseEntity.ok(cust);          // 200 success
                } else {
                    return ResponseEntity.status(401).body("Bad credentials");
                }
            })
            .orElse(ResponseEntity.status(401).body("Bad credentials"));
    }
}
