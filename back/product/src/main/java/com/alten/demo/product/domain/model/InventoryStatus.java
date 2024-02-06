package com.alten.demo.product.domain.model;

public enum InventoryStatus {
    OUTOFSTOCK("Out of Stock"),
    INSTOCK("In Stock"),
    LOWSTOCK("Low Stock");

    public final String displayValue;

    InventoryStatus(String inputValue) {
        this.displayValue = inputValue;
    }
}
