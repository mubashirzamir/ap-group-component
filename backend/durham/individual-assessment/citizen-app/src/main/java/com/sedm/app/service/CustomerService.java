package com.sedm.app.service;

import com.sedm.app.bean.Customer;
import com.sedm.app.entities.CustomerEntity;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    CustomerEntity updateCustomer(Customer customer);

    boolean deleteCustomer(String customerId);

    CustomerEntity findCustomerByNameAndPassword(Customer customer);

    CustomerEntity findByUserId(String userId);

}
