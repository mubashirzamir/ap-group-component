package com.sedm.app.repository;

import com.sedm.app.bean.Customer;
import com.sedm.app.entities.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity,String> {

    CustomerEntity findByUserId(String userId);
}
