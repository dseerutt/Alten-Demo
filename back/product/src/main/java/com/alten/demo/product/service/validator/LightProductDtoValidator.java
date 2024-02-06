package com.alten.demo.product.service.validator;

import com.alten.demo.product.domain.exception.ProductNotFoundException;
import com.alten.demo.product.domain.exception.ValidationException;
import com.alten.demo.product.domain.model.InventoryStatus;
import com.alten.demo.product.domain.model.Product;
import com.alten.demo.product.dto.LightProductDto;
import com.alten.demo.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LightProductDtoValidator {
    private static final String POST_ID_ALREADY_SET_MESSAGE = "Id cannot be set";
    private static final String PRODUCT_NOT_FOUND_ID_MESSAGE = "Product with id %s was not found";
    private static final String TOO_LONG_MESSAGE = "%s is limited to %s characters";
    private static final String INVENTORY_STATUS_QUANTITY = "With patch, please set both Inventory Status and Quantity";
    private static final String WRONG_INVENTORY_STATUS = "Inventory status must be either INSTOCK, OUTOFSTOCK, or LOWSTOCK but it was %s";
    private static final String WRONG_INVENTORY_STATUS_WITH_QUANTITY = "Inventory status cannot be %s with quantity %s";
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_CATEGORY_LENGTH = 50;
    private static final int MAX_IMAGE_LENGTH = 255;
    private static final String DESCRIPTION = "Description";
    private static final String NAME = "Name";
    private static final String CATEGORY = "Category";
    private static final String IMAGE = "Image";

    @Autowired
    private ProductRepository productRepository;

    protected void validate(LightProductDto lightProductDto, boolean patch) {
        var id = lightProductDto.getId();
        if (id != null)
            throw new ValidationException(POST_ID_ALREADY_SET_MESSAGE);

        var name = lightProductDto.getName();
        if (StringUtils.isNotBlank(name) && name.length() >= MAX_NAME_LENGTH)
            throw new ValidationException(TOO_LONG_MESSAGE.formatted(NAME, MAX_DESCRIPTION_LENGTH));

        var description = lightProductDto.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() >= MAX_DESCRIPTION_LENGTH)
            throw new ValidationException(TOO_LONG_MESSAGE.formatted(DESCRIPTION, MAX_DESCRIPTION_LENGTH));

        var category = lightProductDto.getCategory();
        if (StringUtils.isNotBlank(category) && category.length() >= MAX_CATEGORY_LENGTH)
            throw new ValidationException(TOO_LONG_MESSAGE.formatted(CATEGORY, MAX_CATEGORY_LENGTH));

        var image = lightProductDto.getImage();
        if (StringUtils.isNotBlank(image) && image.length() >= MAX_CATEGORY_LENGTH)
            throw new ValidationException(TOO_LONG_MESSAGE.formatted(IMAGE, MAX_IMAGE_LENGTH));

        var quantity = lightProductDto.getQuantity();
        if (quantity == null && !patch) {
            lightProductDto.setQuantity(0);
        }
        if (patch && (lightProductDto.getQuantity() == null ^ lightProductDto.getInventoryStatus() == null)) {
            throw new ValidationException(INVENTORY_STATUS_QUANTITY);
        }

        validateInventoryStatus(lightProductDto, patch);
    }

    public void validateInventoryStatus(LightProductDto lightProductDto, boolean patch) {
        var inventoryStatus = lightProductDto.getInventoryStatus();
        if (!patch && inventoryStatus == null) {
            throw new ValidationException(WRONG_INVENTORY_STATUS.formatted("null"));
        }
        if (inventoryStatus == null) {
            return;
        }
        try {
            lightProductDto.setInventoryStatus(InventoryStatus.valueOf(inventoryStatus).toString());
        } catch (IllegalArgumentException exception) {
            throw new ValidationException(WRONG_INVENTORY_STATUS.formatted(inventoryStatus), exception);
        }
        var quantity = lightProductDto.getQuantity();
        if (quantity > 0 &&
                InventoryStatus.OUTOFSTOCK.displayValue.equals(lightProductDto.getInventoryStatus())) {
            throw new ValidationException(WRONG_INVENTORY_STATUS_WITH_QUANTITY.formatted(inventoryStatus, quantity));
        }
        if (quantity == 0 &&
                (InventoryStatus.LOWSTOCK.displayValue.equals(lightProductDto.getInventoryStatus()) ||
                        InventoryStatus.INSTOCK.displayValue.equals(lightProductDto.getInventoryStatus()))) {
            throw new ValidationException(WRONG_INVENTORY_STATUS_WITH_QUANTITY.formatted(inventoryStatus, quantity));
        }
    }

    public Product validatePatch(LightProductDto lightProductDto, int id) {
        validate(lightProductDto, true);
        lightProductDto.setId(id);

        return productRepository.findById(lightProductDto.getId())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_ID_MESSAGE.formatted(lightProductDto.getId())));
    }
}
