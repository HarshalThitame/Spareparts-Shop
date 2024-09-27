package com.sparesparts.entity.helper;

import lombok.Data;

@Data
public class BrandAndModelDiscountRequest {
    private Long brand;
    private Long model;
    private int retailerDiscount;
    private int mechanicDiscount;
    private int customerDiscount;
}
