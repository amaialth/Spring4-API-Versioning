package com.anbuzhobbiez.apiversion.service;

import com.anbuzhobbiez.apiversion.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductService implements IProductService{
    // In-memory "Database"
    private final Map<Long, Product> repository = new ConcurrentHashMap<>();

    public ProductService() {
        // Mock Data: Initializing with some products
        repository.put(1L, new Product(1L, "Laptop", "High-end gaming laptop", 1200.00));
        repository.put(2L, new Product(2L, "Smartphone", "Latest OLED flagship", 800.00));
        repository.put(3L, new Product(3L, "Headphones", "Noise-cancelling wireless", 250.00));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(repository.values());
    }

    public Product getProductById(Long id) {
        return repository.get(id);
    }
}
