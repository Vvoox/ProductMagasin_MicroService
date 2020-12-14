package com.example.billingservice.controllers;

import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.feign.ProductRestClient;
import com.example.billingservice.models.Bill;
import com.example.billingservice.repositories.BillRepository;
import com.example.billingservice.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillingController {

    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private BillRepository billRepository;
    private CustomerRestClient customerServiceClient;
    private ProductRestClient productServiceClient;

    @GetMapping("")
    List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @GetMapping("/full/{id}")
    Bill getBill(@PathVariable long id) {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found with id " + id));
        bill.setCustomer(customerServiceClient.getCustomer(bill.getCustomerId()));
        bill.setProductItems(productItemRepository.findByBillId(bill.getId()));
        bill.getProductItems().forEach(p -> {
            p.setProduct(productServiceClient.getProduct(p.getProductId()));
        });

        return bill;
    }
}
