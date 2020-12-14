package com.example.costumerservice.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "mobile", types = Customer.class)
public interface CustomerProjectionMobile {
    String getName();
}
