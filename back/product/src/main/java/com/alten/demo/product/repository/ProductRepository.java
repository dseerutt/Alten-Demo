package com.alten.demo.product.repository;

import com.alten.demo.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int id);

    List<Product> findAll();

    void deleteById(int id);

    boolean existsByCode(String code);
}
