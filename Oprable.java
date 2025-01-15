package com.acc.service;

import com.acc.model.Product;
import java.util.List;

public interface Oprable {
    void addProduct();
    List<Product> findAll();
    Product find(int id);
    void updateProduct();
    void deleteProduct();
    void searchProduct();
}
