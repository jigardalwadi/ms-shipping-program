package com.ebay.api.v1.repository;

import com.ebay.api.v1.entity.ProductEntity;
import com.ebay.api.v1.entity.ProductIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, ProductIdentity> {

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.productName) = LOWER(?1) AND p.price >= ?3 AND p.productIdentity.category = ?2 AND p.productIdentity.sellerId = ?4 ")
    List<ProductEntity> getProductsByFilters(String productName,Integer category,Double price,Long sellerId);
}
