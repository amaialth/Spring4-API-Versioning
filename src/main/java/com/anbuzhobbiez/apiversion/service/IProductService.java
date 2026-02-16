package com.anbuzhobbiez.apiversion.service;

import com.anbuzhobbiez.apiversion.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
}
