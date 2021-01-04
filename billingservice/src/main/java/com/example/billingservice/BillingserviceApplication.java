package com.example.billingservice;

import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.feign.ProductRestClient;
import com.example.billingservice.models.Bill;
import com.example.billingservice.models.Customer;
import com.example.billingservice.models.Product;
import com.example.billingservice.models.ProductItem;
import com.example.billingservice.repositories.BillRepository;
import com.example.billingservice.repositories.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log4j2
@EnableFeignClients
@RequiredArgsConstructor
public class BillingserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingserviceApplication.class, args);
    }

     private final BillRepository billRepository;
     private final ProductItemRepository productItemRepository;
    private final ProductRestClient productServiceClient;
    private CustomerRestClient customerServiceClient;


    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            ProductRestClient productServiceClient,
                            CustomerRestClient customerServiceClient) {
        return args -> {
            Product product = productServiceClient.getProduct(1L);
            Customer customer = customerServiceClient.getCustomer(1L);
            ProductItem productItem = productItemRepository.save(ProductItem.builder()
                    .price(2000)
                    .quantity(300)
                    .product(product)
                    .build());
            List<ProductItem> productItemList = new ArrayList<>();
            productItemList.add(productItem);
            log.info(productItem.toString());
            Bill bill = billRepository.save(Bill.builder()
                    .productItems(productItemList)
                    .date(LocalDate.now())
                    .customer(customer)
                    .build());
            log.info(bill);


        };

    }
}
