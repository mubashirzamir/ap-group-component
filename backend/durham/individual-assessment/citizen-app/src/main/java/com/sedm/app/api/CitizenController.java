package com.sedm.app.api;

import com.netflix.discovery.converters.Auto;
import com.sedm.app.bean.Customer;
import com.sedm.app.bean.Reading;
import com.sedm.app.entities.CustomerEntity;
import com.sedm.app.entities.ReadingEntity;
import com.sedm.app.repository.ReadingRepository;
import com.sedm.app.service.CustomerService;
import com.sedm.app.service.ReadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/citizen/api")
@Slf4j
public class CitizenController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReadingService readingService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.gateway.url:''}")
    private String apiGatewayUrl;
    @Value("${ep.path.base:''}")
    private String baseUrl;

    @RequestMapping("/user")
    public String generateApi(){
        return "HELLO WORLD";
    }

    @PostMapping("/reading")
    public ResponseEntity<Reading> consumeReading(@RequestBody Reading reading){

        //System.out.println(reading.getUserId()+" - "+reading.getProviderId());
        readingService.createReading(reading);
        restTemplate.postForEntity(apiGatewayUrl+baseUrl,reading,Reading.class);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","201");
        return ResponseEntity.ok().headers(responseHeaders).body(reading);
    }

    @GetMapping("/reading")
    public ResponseEntity<ReadingEntity> getReadings(@RequestParam String userId){

        //System.out.println(reading.getUserId()+" - "+reading.getProviderId());
        ReadingEntity entity = readingService.getReadingByUserId(userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","201");
        return ResponseEntity.ok().headers(responseHeaders).body(entity);
    }
    @PostMapping("/update")
    public ResponseEntity<Reading> consumeReadingUpdate(@RequestBody Reading reading){

        //System.out.println(reading.getUserId()+" - "+reading.getProviderId());
        readingService.updateReading(reading);
       restTemplate.postForEntity(apiGatewayUrl+baseUrl,reading,Reading.class);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","201");
        return ResponseEntity.ok().headers(responseHeaders).body(reading);
    }

    @PostMapping("/user/create")
    public ResponseEntity<Customer> createUser(@RequestBody Customer customer){

        log.info("Customer information: {}",customer.getName());

        Customer customer1 = customerService.createCustomer(customer);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","201");
        return ResponseEntity.ok().headers(responseHeaders).body(customer1);
    }

    @PostMapping("/user/update")
    public ResponseEntity<CustomerEntity> updateUser(@RequestBody Customer customer){

        log.info("Customer information: {}",customer.getName());

        CustomerEntity customerEntity = customerService.updateCustomer(customer);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","200");
        return ResponseEntity.ok().headers(responseHeaders).body(customerEntity);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<String> updateUser(@RequestParam String customerId){

        log.info("Customer information: {}",customerId);

        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/user/validate")
    public ResponseEntity<CustomerEntity> validateUser(@RequestBody Customer customer){

        CustomerEntity customerEntity = customerService.findCustomerByNameAndPassword(customer);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","201");
        return ResponseEntity.ok().headers(responseHeaders).body(customerEntity);
    }

    @GetMapping("/user/get")
    public ResponseEntity<CustomerEntity> validateUser(@RequestParam String userId){

        CustomerEntity customerEntity = customerService.findByUserId(userId);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","200");
        return ResponseEntity.ok().headers(responseHeaders).body(customerEntity);
    }

    @GetMapping("/auto/reading")
    public ResponseEntity<String> getGeneratedReading(@RequestParam("electricityConsumption") Long electricityConsumption){

        System.out.println(electricityConsumption);

        return ResponseEntity.ok("Success");
    }
}
