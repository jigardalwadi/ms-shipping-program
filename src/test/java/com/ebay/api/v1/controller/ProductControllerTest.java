package com.ebay.api.v1.controller;

import com.ebay.api.v1.model.ProductEligibilityResource;
import com.ebay.api.v1.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static com.ebay.api.v1.util.constant.ENROLLED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController subject;

    @Mock
    private ProductService productService;

    @Captor
    private ArgumentCaptor<String> productCaptur;

    @Captor
    private ArgumentCaptor<String> sellerNameCaptur;

    @Captor
    private ArgumentCaptor<Integer> categoryCaptur;

    @Captor
    private ArgumentCaptor<Double> priceCaptur;

    @Test
    public void verifyProduct() {
        // given
        when(productService.verifyProductForShipping(anyString(),anyString(),anyInt(),anyDouble())).thenReturn(getProductEligibilityResource());

        // when
        ResponseEntity<ProductEligibilityResource> resource = subject.verifyProduct("product", "seller", 1, 10.00);

        // then
        verify(productService).verifyProductForShipping(productCaptur.capture(),sellerNameCaptur.capture(),categoryCaptur.capture(),priceCaptur.capture());
        assertEquals(resource.getBody().getProductName(),getProductEligibilityResource().getProductName());
        assertEquals(resource.getBody().getShippingEligibility(),getProductEligibilityResource().getShippingEligibility());
    }

    public ProductEligibilityResource getProductEligibilityResource(){
        ProductEligibilityResource resource = new ProductEligibilityResource();
        resource.setProductName("product");
        resource.setShippingEligibility(ENROLLED);
        return resource;
    }
}