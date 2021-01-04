package com.example.costumerservice;

import com.example.costumerservice.models.Customer;
import com.example.costumerservice.repositories.CustomerRepository;
import com.example.costumerservice.services.Consumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CostumerserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostumerserviceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            Consumer consumer = new Consumer();
            customerRepository.save(Customer.builder().name("Khalil").email("khalil@gmail.com").build());
            customerRepository.save(Customer.builder().name("Oussama").email("oussama@gmail.com").build());
            customerRepository.save(Customer.builder().name("Zakaria").email("zakaria@gmail.com").build());
        };
    }
}
