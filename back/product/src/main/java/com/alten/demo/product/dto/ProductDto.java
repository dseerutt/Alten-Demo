package com.alten.demo.product.dto;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ProductDto extends LightProductDto {
    public String code;
}
