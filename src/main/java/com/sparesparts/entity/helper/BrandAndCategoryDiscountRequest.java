package com.sparesparts.entity.helper;

import lombok.Data;

@Data
public class BrandAndCategoryDiscountRequest {
    private Long brand;
    private Long category;
    private int retailerDiscount;
    private int mechanicDiscount;
    private int customerDiscount;
}
