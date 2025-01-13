package com.sedm.app.service;

import com.sedm.app.bean.Customer;
import com.sedm.app.entities.CustomerEntity;
import com.sedm.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {

        CustomerEntity customerPersistant = new CustomerEntity();
        customerPersistant.setName(customer.getName());
        customerPersistant.setCity(customer.getCity());
        customerPersistant.setMeterId(customer.getMeterId());
        customerPersistant.setAge(customer.getAge());
        customerPersistant.setEmailId(customer.getEmailId());
        customerPersistant.setPasswd(customer.getPasswd());
        customerPersistant.setMobile(customer.getMobile());

        customerPersistant = customerRepository.insert(customerPersistant);

        customer.setUserId(customerPersistant.getUserId());

        return customer;

    }

    @Override
    public CustomerEntity updateCustomer(Customer customer) {
        CustomerEntity customerEntity = customerRepository.findByUserId(customer.getUserId());

        customerEntity.setMeterId(customer.getMeterId());
        customerEntity.setMobile(customer.getMobile());

       customerEntity = customerRepository.save(customerEntity);

        return customerEntity;

    }

    @Override
    public boolean deleteCustomer(String customerId) {

        customerRepository.deleteById(customerId);

        return true;
    }

    @Override
    public CustomerEntity findCustomerByNameAndPassword(Customer customer) {

        List<CustomerEntity> customerList = customerRepository.findAll();

        for(CustomerEntity customerEntity:customerList){
            if(customerEntity.getEmailId().equals(customer.getEmailId()) && customerEntity.getPasswd().equals(customer.getPasswd())){
                return customerEntity;
            }
        }

        return null;
    }

    @Override
    public CustomerEntity findByUserId(String userId) {
        return customerRepository.findByUserId(userId);
    }
}
