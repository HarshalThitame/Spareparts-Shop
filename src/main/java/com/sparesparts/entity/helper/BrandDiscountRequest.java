package com.sparesparts.entity.helper;

import lombok.Data;

@Data
public class BrandDiscountRequest {
    private Long brand;
    private int retailerDiscount;
    private int mechanicDiscount;
    private int customerDiscount;
}
