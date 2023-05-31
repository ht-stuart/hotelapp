package service;

import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.HashMap;

public class CustomerService {


    private static CustomerService INSTANCE;

    HashMap<String, Customer> allUsers = new HashMap();


    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }

        return INSTANCE;
    }


    public void addCustomer(String firstName, String lastName, String email) {
        try {
            Customer newUser = new Customer(firstName, lastName, email);
            if (allUsers.isEmpty()) {
                allUsers.put(email, newUser);
                System.out.println("Account Created!" + "\n" + newUser.toString());
            } else {
                if (allUsers.containsKey(email)) {
                    System.out.println("Email already in use");
                } else {
                    allUsers.put(email, newUser);
                    System.out.println("Account Created!" + "\n" + newUser.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email address. Please try again.");
        }

    }


    public Customer getCustomer(String email) {
        return allUsers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        if (allUsers.isEmpty()) {
            System.out.println("No users currently in system. ");
        }
        return allUsers.values();
    }
}


