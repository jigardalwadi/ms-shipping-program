package com.ebay.api.v1.repository;

import com.ebay.api.v1.entity.SellerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends CrudRepository<SellerEntity, Long> {

    @Query("SELECT s FROM SellerEntity s WHERE s.sellerName =?1 AND s.activeRecordIndicator = ?2")
    List<SellerEntity> getSellerByName(String sellerName,String activeRecordIndicator);
}
