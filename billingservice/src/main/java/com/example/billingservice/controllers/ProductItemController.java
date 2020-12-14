package com.example.billingservice.controllers;

import com.example.billingservice.models.ProductItem;
import com.example.billingservice.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product-items")
public class ProductItemController {

    @Autowired
    ProductItemRepository productItemRepository;

    @GetMapping("")
    List<ProductItem> getAllProductItem() {
        return productItemRepository.findAll();
    }
}
