package com.example.billingservice.controllers;

import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.feign.ProductRestClient;
import com.example.billingservice.models.Bill;
import com.example.billingservice.repositories.BillRepository;
import com.example.billingservice.repositories.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bills/api")
@RequiredArgsConstructor
public class BillingController {

    private final ProductItemRepository productItemRepository;
    private final BillRepository billRepository;
    private final CustomerRestClient customerServiceClient;
    private final ProductRestClient productServiceClient;
    private final ProductRestClient productRestClient;

    @GetMapping("")
    List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @GetMapping("/message")
    String getMsg(){
        return productRestClient.getMessage();
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
