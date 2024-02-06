package com.alten.demo.product.service.validator;

import com.alten.demo.product.domain.exception.ProductAlreadyExistsException;
import com.alten.demo.product.domain.exception.ValidationException;
import com.alten.demo.product.dto.ProductDto;
import com.alten.demo.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDtoValidator {
    private static final String POST_ID_ALREADY_SET_MESSAGE = "Id cannot be set when using POST";
    private static final String PRODUCT_ALREADY_EXISTS_CODE_MESSAGE = "Product with code %s already exists";
    private static final String TOO_LONG_MESSAGE = "%s is limited to %s characters";
    private static final int MAX_CODE_LENGTH = 10;
    private static final String CODE = "Code";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LightProductDtoValidator lightProductDtoValidator;

    protected void validate(ProductDto productDto) {
        var code = productDto.getCode();
        if (StringUtils.isNotBlank(code) && code.length() >= MAX_CODE_LENGTH)
            throw new ValidationException(TOO_LONG_MESSAGE.formatted(CODE, MAX_CODE_LENGTH));
        lightProductDtoValidator.validate(productDto, false);
    }

    public void validatePost(ProductDto productDto) {
        if (productDto.getId() != null)
            throw new ValidationException(POST_ID_ALREADY_SET_MESSAGE);
        if (productRepository.existsByCode(productDto.getCode())) {
            throw new ProductAlreadyExistsException(PRODUCT_ALREADY_EXISTS_CODE_MESSAGE.formatted(productDto.getCode()));
        }
        validate(productDto);
    }

    public boolean validateDelete(int productId) {
        return productRepository.findById(productId).isPresent();
    }
}
