package com.alten.demo.product.dto.mapper;

import com.alten.demo.product.domain.model.Product;
import com.alten.demo.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductDtoMapper {

    @Mapping(source = "price", target = "productInventory.price")
    @Mapping(source = "rating", target = "productInventory.rating")
    @Mapping(source = "quantity", target = "productInventory.quantity")
    @Mapping(source = "inventoryStatus", target = "productInventory.inventoryStatus")
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(source = "product.productInventory.price", target = "price")
    @Mapping(source = "product.productInventory.rating", target = "rating")
    @Mapping(source = "product.productInventory.quantity", target = "quantity")
    @Mapping(source = "product.productInventory.inventoryStatus", target = "inventoryStatus")
    ProductDto productToProductDto(Product product);
}
