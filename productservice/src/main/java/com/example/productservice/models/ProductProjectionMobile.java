package com.example.productservice.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "mobile", types = Product.class)
public interface ProductProjectionMobile {
    String getName();
}
