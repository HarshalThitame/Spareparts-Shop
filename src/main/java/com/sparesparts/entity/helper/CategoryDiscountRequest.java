package com.sparesparts.entity.helper;

import lombok.Data;

@Data
public class CategoryDiscountRequest {
    private Long category;
    private int retailerDiscount;
    private int mechanicDiscount;
    private int customerDiscount;
}
