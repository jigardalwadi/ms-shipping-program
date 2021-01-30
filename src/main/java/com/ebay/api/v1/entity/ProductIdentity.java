package com.ebay.api.v1.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductIdentity implements Serializable {

    @Column(name = "PRDCT_ID",nullable = false)
    private Long productId;

    @Column(name = "SLR_ID",nullable = false)
    private Long sellerId;

    @Column(name = "PRDCT_CTGR", nullable = false)
    private Integer category;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
