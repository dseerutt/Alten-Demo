package com.alten.demo.product.service.impl;

import com.alten.demo.product.domain.exception.ProductNotFoundException;
import com.alten.demo.product.domain.model.Product;
import com.alten.demo.product.dto.LightProductDto;
import com.alten.demo.product.dto.ProductDto;
import com.alten.demo.product.dto.mapper.ProductDtoMapper;
import com.alten.demo.product.repository.ProductRepository;
import com.alten.demo.product.service.ProductService;
import com.alten.demo.product.service.validator.LightProductDtoValidator;
import com.alten.demo.product.service.validator.ProductDtoValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDtoValidator productDtoValidator;
    @Autowired
    private LightProductDtoValidator lightProductDtoValidator;

    private final ProductDtoMapper productDtoMapper = Mappers.getMapper(ProductDtoMapper.class);
    private final static String PRODUCT_NOT_FOUND_ID_MESSAGE = "Product not found with id %s";

    @Override
    public ProductDto getProduct(int id) {
        return productDtoMapper.productToProductDto(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_ID_MESSAGE.formatted(id))));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productDtoMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        productDtoValidator.validatePost(productDto);
        var product = productDtoMapper.productDtoToProduct(productDto);
        return productDtoMapper.productToProductDto(productRepository.save(product));
    }

    @Override
    public ProductDto patchProduct(LightProductDto lightProductDto, int productId) {
        // patch and get all to fix
        var element = lightProductDtoValidator.validatePatch(lightProductDto, productId);
        patchProduct(lightProductDto, element);
        var savedElement = productRepository.save(element);
        return productDtoMapper.productToProductDto(savedElement);
    }

    private void patchProduct(LightProductDto lightProductDto, Product product) {
        if (lightProductDto.getName() != null) {
            product.setName(lightProductDto.getName());
        }
        if (lightProductDto.getDescription() != null) {
            product.setDescription(lightProductDto.getDescription());
        }
        if (lightProductDto.getPrice() != null) {
            product.getProductInventory().setPrice(lightProductDto.getPrice());
        }
        if (lightProductDto.getQuantity() != null) {
            product.getProductInventory().setQuantity(lightProductDto.getQuantity());
        }
        if (lightProductDto.getInventoryStatus() != null) {
            product.getProductInventory().setInventoryStatus(lightProductDto.getInventoryStatus());
        }

        if (lightProductDto.getCategory() != null) {
            product.setCategory(lightProductDto.getCategory());
        }
        if (lightProductDto.getImage() != null) {
            product.setImage(lightProductDto.getImage());
        }
        if (lightProductDto.getRating() != null) {
            product.getProductInventory().setRating(lightProductDto.getRating());
        }
    }

    @Override
    public void removeProduct(int productId) {
        if (productDtoValidator.validateDelete(productId)) {
            productRepository.deleteById(productId);
        }
        // Nothing to do if doesn't exist
    }
}
