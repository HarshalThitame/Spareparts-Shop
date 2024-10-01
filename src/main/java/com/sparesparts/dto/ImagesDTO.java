package com.sparesparts.dto;

import lombok.Data;

@Data
public class ImagesDTO {
    private String url;
    private Long productId; // Only the product ID
}
