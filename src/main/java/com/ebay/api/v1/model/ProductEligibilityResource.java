package com.ebay.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "Product Eligibility Resource")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductEligibilityResource {

    @ApiModelProperty(name = "Product name", value = "Product name", required = true)
    @NotBlank
    private String productName;

    @ApiModelProperty(name = "Shipping eligibility", value = "Shipping eligibility", required = true)
    @NotBlank
    private String shippingEligibility;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShippingEligibility() {
        return shippingEligibility;
    }

    public void setShippingEligibility(String shippingEligibility) {
        this.shippingEligibility = shippingEligibility;
    }

    // this fields can be added based on the requirements
    // category
    // price
    // owner
    // description
    // active record indicator
    // add id
    // update id
    // add timestamp
    // update timestamp

}
