package com.sparesparts.entity.helper;

import lombok.Data;

@Data
public class DiscountRequest {
    private Long product;
    private int retailerDiscount;
    private int mechanicDiscount;
    private int customerDiscount;
}
