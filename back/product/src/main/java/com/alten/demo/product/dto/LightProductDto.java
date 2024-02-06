package com.alten.demo.product.dto;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class LightProductDto {
    //no code
    public Integer id;
    public String name;
    public String description;
    public Integer price;
    public Integer quantity;
    public String inventoryStatus;
    public String category;
    public String image;
    public Double rating;
}
