package com.ebay.api.v1.service;

import com.ebay.api.v1.entity.PersonEnrollmentEntity;
import com.ebay.api.v1.entity.ProductEntity;
import com.ebay.api.v1.entity.ProductIdentity;
import com.ebay.api.v1.entity.SellerEntity;
import com.ebay.api.v1.exception.SpException;
import com.ebay.api.v1.mapper.ProductEntityMapper;
import com.ebay.api.v1.model.ProductEligibilityResource;
import com.ebay.api.v1.repository.PersonEnrollmentRepository;
import com.ebay.api.v1.repository.ProductRepository;
import com.ebay.api.v1.repository.SellerRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ebay.api.v1.util.constant.ENROLLED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    @Spy
    private ProductService subject;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private PersonEnrollmentRepository personEnrollmentRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @Captor
    private ArgumentCaptor<String> productCaptur;

    @Captor
    private ArgumentCaptor<String> sellerNameCaptur;

    @Captor
    private ArgumentCaptor<Integer> categoryCaptur;

    @Captor
    private ArgumentCaptor<Double> priceCaptur;

    @Captor
    private ArgumentCaptor<Long> sellerIdCaptur;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void verifyProductForShipping() {
        // given
        List<ProductEntity> productEntityList = new ArrayList<>();
        productEntityList.add(getProducts());
        doReturn(1L).when(subject).getEnrolledSellerId("seller");
        when(productRepository.getProductsByFilters(anyString(),anyInt(),anyDouble(),anyLong())).thenReturn(productEntityList);
        when(productEntityMapper.shippingEligibility(anyString(),anyString())).thenReturn(getEnrolledResource());

        // when
        ProductEligibilityResource resource = subject.verifyProductForShipping("product", "seller", 2, 10000.00);

        // then
        verify(subject).getEnrolledSellerId(sellerNameCaptur.capture());
        verify(productRepository).getProductsByFilters(productCaptur.capture(),categoryCaptur.capture(),priceCaptur.capture(),sellerIdCaptur.capture());
        assertEquals(resource.getProductName(),getEnrolledResource().getProductName());
        assertEquals(resource.getShippingEligibility(),getEnrolledResource().getShippingEligibility());

    }

    @Test
    public void verifyProductForShipping_sellerNotFound() {
        // given
        doReturn(null).when(subject).getEnrolledSellerId(anyString());
        when(productEntityMapper.shippingEligibility(anyString(),anyString())).thenReturn(getUnEnrolledResource());

        // when
        ProductEligibilityResource resource = subject.verifyProductForShipping("product", "seller", 2, 10000.00);

        // then
        verify(subject).getEnrolledSellerId(sellerNameCaptur.capture());
        assertEquals(resource.getProductName(),getUnEnrolledResource().getProductName());
        assertEquals(resource.getShippingEligibility(),getUnEnrolledResource().getShippingEligibility());

    }

    @Test
    public void verifyProductForShipping_categoryNotApproved() {
        // given
        when(productEntityMapper.shippingEligibility(anyString(),anyString())).thenReturn(getUnEnrolledResource());

        // when
        ProductEligibilityResource resource = subject.verifyProductForShipping("product", "seller", 20, 10000.00);

        // then
        assertEquals(resource.getProductName(),getUnEnrolledResource().getProductName());
        assertEquals(resource.getShippingEligibility(),getUnEnrolledResource().getShippingEligibility());

    }

    @Test
    public void verifyProductForShipping_PriceNotMatched_UnEnrolled() {
        // given
        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity products = getProducts();
        products.setPrice(2000.00);
        productEntityList.add(products);
        doReturn(1L).when(subject).getEnrolledSellerId(anyString());
        when(productRepository.getProductsByFilters(anyString(),anyInt(),anyDouble(),anyLong())).thenReturn(productEntityList);
        when(productEntityMapper.shippingEligibility(anyString(),anyString())).thenReturn(getUnEnrolledResource());

        // when
        ProductEligibilityResource resource = subject.verifyProductForShipping("product", "seller", 2, 10000.00);

        // then
        verify(subject).getEnrolledSellerId(sellerNameCaptur.capture());
        verify(productRepository).getProductsByFilters(productCaptur.capture(),categoryCaptur.capture(),priceCaptur.capture(),sellerIdCaptur.capture());
        assertEquals(resource.getProductName(),getUnEnrolledResource().getProductName());
        assertEquals(resource.getShippingEligibility(),getUnEnrolledResource().getShippingEligibility());
    }


    @Test
    public void verifyProductForShipping_productNotFound() {
        // given
        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity products = getProducts();
        products.setPrice(2000.00);
        productEntityList.add(products);
        doReturn(1L).when(subject).getEnrolledSellerId(anyString());
        when(productRepository.getProductsByFilters(anyString(),anyInt(),anyDouble(),anyLong())).thenReturn(null);
        expectedException.expect(SpException.class);
        expectedException.expectMessage("Product not found");

        // when
        ProductEligibilityResource resource = subject.verifyProductForShipping("product", "seller", 2, 10000.00);

        // then
        verify(subject).getEnrolledSellerId(sellerNameCaptur.capture());
    }

    @Test
    public void getEnrolledSellerId() {
        // given
        SellerEntity sellerEntity = getSellerEntity();
        List<SellerEntity> sellerEntityList = new ArrayList<>();
        sellerEntityList.add(sellerEntity);
        when(sellerRepository.getSellerByName(anyString(),anyString())).thenReturn(sellerEntityList);
        when(personEnrollmentRepository.getEnrollmentRecords(anyLong(),anyString())).thenReturn(getPersonEnrollmentEntity());

        // when
        Long seller = subject.getEnrolledSellerId("seller");

        // then
        assertEquals(seller,sellerEntity.getSellerId());
    }

    @Test
    public void getEnrolledSellerId_noSellerFound(){
        expectedException.expect(SpException.class);
        expectedException.expectMessage("Seller not found");

        when(sellerRepository.getSellerByName(anyString(),anyString())).thenReturn(Collections.emptyList());

        subject.getEnrolledSellerId("seller");

    }

    @Test
    public void getEnrolledSellerId_multipleSellersFound(){
        expectedException.expect(SpException.class);
        expectedException.expectMessage("Multiple sellers found");

        SellerEntity sellerEntity = getSellerEntity();
        List<SellerEntity> sellerEntityList = new ArrayList<>();
        sellerEntityList.add(sellerEntity);
        sellerEntityList.add(sellerEntity);
        when(sellerRepository.getSellerByName(anyString(),anyString())).thenReturn(sellerEntityList);

        subject.getEnrolledSellerId("seller");

    }

    public SellerEntity getSellerEntity(){
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setSellerId(1L);
        sellerEntity.setSellerName("seller");
        sellerEntity.setActiveRecordIndicator("Y");
        return sellerEntity;

    }

    public ProductEligibilityResource getEnrolledResource(){
        ProductEligibilityResource resource = new ProductEligibilityResource();
        resource.setProductName("product");
        resource.setShippingEligibility(ENROLLED);
        return resource;
    }

    public ProductEligibilityResource getUnEnrolledResource(){
        ProductEligibilityResource resource = new ProductEligibilityResource();
        resource.setProductName("product");
        resource.setShippingEligibility("UNENROLLED");
        return resource;
    }

    public ProductEntity getProducts(){
        ProductEntity entity = new ProductEntity();
        entity.setActiveRecordIndicator("Y");
        entity.setDescription("product description");
        entity.setPrice(5000.00);
        entity.setProductName("product");
        ProductIdentity identity = new ProductIdentity();
        identity.setCategory(1);
        identity.setProductId(1L);
        identity.setSellerId(1L);
        entity.setProductIdentity(identity);
        entity.setQuantity(2L);
        return entity;
    }

    public PersonEnrollmentEntity getPersonEnrollmentEntity(){
        PersonEnrollmentEntity personEnrollmentEntity = new PersonEnrollmentEntity();
        personEnrollmentEntity.setActiveRecordIndicator("Y");
        personEnrollmentEntity.setEnrollmentStatus(ENROLLED);
        return personEnrollmentEntity;
    }
}