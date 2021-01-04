package com.example.productservice;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.service.Producer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            Producer producer = new Producer();
            productRepository.save(Product.builder().name("HP").price(8000.00).quantity(200).build());
            productRepository.save(Product.builder().name("DELL").price(7000.00).quantity(100).build());
            productRepository.save(Product.builder().name("ThinkPad").price(6000.00).quantity(300).build());
        };
    }
}
