package com.example.costumerservice.controllers;

import com.example.costumerservice.models.Customer;
import com.example.costumerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers/api")
public class CustomerController {
    @Autowired
    private CustomerRepository costumerRepository;

    @Autowired
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @GetMapping("")
    public List<Customer> getAllCustomer() {
        return costumerRepository.findAll();
    }

    @GetMapping("/hello")
    public String gettingMsg(){
         return this.restTemplate()
                 .getForEntity("http://localhost:8888/PRODUCT-SERVICE/products/api/messagezip",
                         String.class)
                 .getBody();
    }

    @GetMapping("/{id}")
    public Customer getCostumer(@PathVariable long id) {
        return costumerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Costumer not found with id " + id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCostumer(@RequestBody Customer customer) {
        costumerRepository.save(customer);
        return ResponseEntity.ok("added successfully");
    }

    @PutMapping("/{id}/modify")
    public ResponseEntity<?> modifyCostumer(@PathVariable long id, @RequestBody Customer customer) {
        Customer oldCostumer = getCostumer(id);
        customer.setId(customer.getId());
        costumerRepository.save(customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> removeCostumer(@PathVariable long id) {
        Customer customer = getCostumer(id);
        costumerRepository.delete(customer);
        return ResponseEntity.ok("custumer with id " + id + " removed successfully");
    }


}
