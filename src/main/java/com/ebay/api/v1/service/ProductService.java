package com.ebay.api.v1.service;

import com.ebay.api.v1.entity.PersonEnrollmentEntity;
import com.ebay.api.v1.entity.ProductEntity;
import com.ebay.api.v1.entity.SellerEntity;
import com.ebay.api.v1.exception.SpException;
import com.ebay.api.v1.mapper.ProductEntityMapper;
import com.ebay.api.v1.model.ProductEligibilityResource;
import com.ebay.api.v1.repository.PersonEnrollmentRepository;
import com.ebay.api.v1.repository.ProductRepository;
import com.ebay.api.v1.repository.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.ebay.api.v1.util.constant.*;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private PersonEnrollmentRepository personEnrollmentRepository;
    private ProductEntityMapper productEntityMapper;

    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository,
                          PersonEnrollmentRepository personEnrollmentRepository, ProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.personEnrollmentRepository = personEnrollmentRepository;
        this.productEntityMapper = productEntityMapper;
    }
    /*
    * check for the category
    * check for the seller id
    * get all the product using category, seller id, price
    * compare price of the product with min price
    * */
    public ProductEligibilityResource verifyProductForShipping(String productName, String sellerName, Integer category, Double price) {
        // get all the products based on the productName category and price
        if (Categories.contains(category)) {
            // check for sellers enrollment and get sellers id
            Long enrolledSellerId = this.getEnrolledSellerId(sellerName);
            if(enrolledSellerId == null){
                LOGGER.info("Seller not found for seller name  {}",sellerName);
            }
            else{
                List<ProductEntity> products = productRepository.getProductsByFilters(productName, category, price,enrolledSellerId);
                if (CollectionUtils.isEmpty(products)) {
                    LOGGER.info("No product found for product name {}", productName);
                    throw new SpException("Product not found",HttpStatus.NOT_FOUND);
                } else {
                    // find the first record for which the price is > min price
                    Optional<ProductEntity> finalProduct = products.stream().filter(product -> product.getPrice() >= MinItemPrice).findFirst();
                    if(finalProduct.isPresent()){
                        LOGGER.info("Product found for the given criteria, product id {}",finalProduct.get().getProductIdentity().getProductId());
                        ProductEligibilityResource productEligibility = productEntityMapper.shippingEligibility(productName, ELIGIBLE);
                        return productEligibility;
                    }
                    else{
                        // no product found for given criteria
                        LOGGER.info("No eligible product found for the shipping program, product name {}", productName);
                        ProductEligibilityResource productEligibility = productEntityMapper.shippingEligibility(productName, NOT_ELIGIBLE);
                        return productEligibility;
                    }
                }
            }
        }
        return productEntityMapper.shippingEligibility(productName, NOT_ELIGIBLE);
    }

    protected Long getEnrolledSellerId(String sellerName) {
        List<SellerEntity> listOfSellers = sellerRepository.getSellerByName(sellerName,"Y");
        if (listOfSellers.isEmpty()) {
            LOGGER.info("Seller not found for name{}",sellerName);
            throw new SpException("Seller not found",HttpStatus.NOT_FOUND);
        } else {
            if (listOfSellers.size() > 1) {
                // There are more then 1 sellers by the name, more details required in the requirement
                // As the request comes from the front-end this scenario is not going to happen
                // if this happens we need more filtering in the request for the seller
                LOGGER.info("Multiple sellers found for name{}",sellerName);
                throw new SpException("Multiple sellers found",HttpStatus.BAD_REQUEST);
            } else {
                // check if the seller is enrolled for shipping program
                SellerEntity sellerEntity = listOfSellers.get(0);
                Long sellerId = sellerEntity.getSellerId();
                PersonEnrollmentEntity enrollmentRecords = personEnrollmentRepository.getEnrollmentRecords(sellerId, SHIPPING_PROGRAM);
                if(enrollmentRecords == null){
                    LOGGER.info("Enrollment record not found for seller {}",sellerName);
                    return null;
                } else{
                    // check enrollment status
                    if(enrollmentRecords.getEnrollmentStatus().equals(ENROLLED)){
                        LOGGER.info("Enrolled seller found for name {}",sellerName);
                        return sellerId;
                    }
                    else{
                        LOGGER.info("Seller is not enrolled, seller {}",sellerName);
                        return null;
                    }
                }
            }
        }
    }

}
