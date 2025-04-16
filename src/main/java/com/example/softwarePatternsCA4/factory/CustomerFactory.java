package com.example.softwarePatternsCA4.factory;

import com.example.softwarePatternsCA4.entity.Customer;

public class CustomerFactory {
    
    // Implementing the factory as a singleton
    private static CustomerFactory instance;
    
    private CustomerFactory() { }
    
    public static CustomerFactory getInstance() {
        if(instance == null) {
            instance = new CustomerFactory();
        }
        return instance;
    }
    
    // Factory method for creating a Customer depending on the userRole 
    
    public Customer createCustomer(String userRole,String username,String password,String shippingAddress, String paymentMethod,
    		String emailAddress) {
        
        if("ADMIN".equalsIgnoreCase(userRole)) {
            System.out.println("Creating an admin customer");
            
            return new Customer(username, password, shippingAddress, paymentMethod, emailAddress, "ADMIN");
        } else {
            System.out.println("Creating a regular customer");
            return new Customer(username, password, shippingAddress, paymentMethod, emailAddress, "CUSTOMER");
        }
    }
}
