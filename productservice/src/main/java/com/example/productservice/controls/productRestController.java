package com.example.productservice.controls;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/api")
public class productRestController {

    @Autowired
    private ProductRepository productRepository;

    //API : Get all products
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //API : Get one product by id and return exception for non-existence
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
    }

    //API : Add one product by id after checked if already exist this id
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Already exist");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Added successfully");
    }

    //API : Modify one product by id
    @PutMapping("/{id}/modify")
    public ResponseEntity<?> addProduct(@PathVariable Long id, @RequestBody Product product) throws ChangeSetPersister.NotFoundException {
        Product oldProduct = getProduct(id);
        product.setId(oldProduct.getId());
        productRepository.save(product);
        return ResponseEntity.ok("Modified successfully");
    }

    //API : Delete product
    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> addProduct(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Product product = getProduct(id);
        productRepository.delete(product);
        return ResponseEntity.ok("Added successfully");
    }
}
