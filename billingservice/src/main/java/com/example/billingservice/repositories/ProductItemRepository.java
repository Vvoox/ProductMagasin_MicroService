package com.example.billingservice.repositories;

import com.example.billingservice.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    List<ProductItem> findByBillId(Long id);
}
