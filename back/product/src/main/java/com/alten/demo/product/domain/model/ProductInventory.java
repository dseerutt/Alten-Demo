package com.alten.demo.product.domain.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "product_inventory")
public class ProductInventory {

    @Id
    @Column(name = "product_id")
    private int id;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "inventory_status")
    private String inventoryStatus;

    @Column(name = "rating")
    private double rating;
}
