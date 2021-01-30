package com.ebay.api.v1.mapper;

import com.ebay.api.v1.model.ProductEligibilityResource;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {
    public ProductEligibilityResource shippingEligibility(String productName, String eligible) {
        ProductEligibilityResource productEligibilityResource = new ProductEligibilityResource();
        productEligibilityResource.setProductName(productName);
        productEligibilityResource.setShippingEligibility(eligible);
        return productEligibilityResource;
    }
}
