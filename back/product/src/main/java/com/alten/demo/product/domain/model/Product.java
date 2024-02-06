package com.alten.demo.product.domain.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductInventory productInventory;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "image")
    private String image;
}
