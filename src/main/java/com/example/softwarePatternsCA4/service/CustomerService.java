package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.Customer;
import com.example.softwarePatternsCA4.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Register a new customer
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Retrieve a customer by username
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    // Update an existing customer
    public Customer updateCustomer(int id, Customer updatedCustomer) {
        return customerRepository.findById(id)
            .map(customer -> {
                customer.setUsername(updatedCustomer.getUsername());
                customer.setPassword(updatedCustomer.getPassword());
                customer.setShippingAddress(updatedCustomer.getShippingAddress());
                customer.setPaymentMethod(updatedCustomer.getPaymentMethod());
                customer.setEmailAddress(updatedCustomer.getEmailAddress());
                customer.setUserRole(updatedCustomer.getUserRole());
                return customerRepository.save(customer);
            })
            .orElse(null);
    }

    // Delete a customer
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
