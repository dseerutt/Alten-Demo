package com.alten.demo.product.service;

import com.alten.demo.product.dto.LightProductDto;
import com.alten.demo.product.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto getProduct(int id);

    List<ProductDto> getAllProducts();

    ProductDto addProduct(ProductDto productDto);

    ProductDto patchProduct(LightProductDto productDto, int productId);

    void removeProduct(int productId);
}
