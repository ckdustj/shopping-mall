package com.shop.dto.product;

import com.shop.dto.ImageFileDTO;
import java.util.List;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int no;
    private String title;
    private String subTitle;
    private int price;
    private int deliveryPrice;
    private LocalDateTime writeDate;
    private Category category;
    private List<ImageFileDTO> productImg;
    private List<ProductOptionDTO> productOptions;
}
