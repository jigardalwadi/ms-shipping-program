package com.ebay.api.v1.controller;

import com.ebay.api.v1.model.ProductEligibilityResource;
import com.ebay.api.v1.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ebay.api.v1.MsShippingProgramApplication.V1_API_ROOT;

@RestController
@RequestMapping(value = V1_API_ROOT)
@Api(tags = "product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Verify product enrollment for shipping",notes = "Service that identifies whether or not an item is eligible for the new shipping program")
    @GetMapping("/products/{productName}/verify")
    public ResponseEntity<ProductEligibilityResource> verifyProduct(@PathVariable @ApiParam(value = "Product Name", required = true) String productName,
                                                                   @RequestParam @ApiParam(value = "Seller Name", required = true) String sellerName,
                                                                   @RequestParam @ApiParam(value = "Product Category", required = true) Integer category,
                                                                   @RequestParam @ApiParam(value = "Product Price", required = true) Double price
    ){
        LOGGER.info("Verify product for product name {} , seller id {}, category {} and amount {}", productName, sellerName, category, price.toString());
        ProductEligibilityResource productEligibility = productService.verifyProductForShipping(productName, sellerName, category, price);
        return new ResponseEntity(productEligibility, HttpStatus.OK);
    }

    // add products using seller id
}
