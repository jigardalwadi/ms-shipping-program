package com.ebay.api.v1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRDCT_ENTITY")
public class ProductEntity extends BaseEntity{

    @EmbeddedId
    private ProductIdentity productIdentity;

    @Column(name = "PRDCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRDCT_PRC")
    private Double price;

    @Column(name = "PRDCT_QNT")
    private Long quantity;

    @Column(name = "PRDCT_DESC")
    private String description;

    @Column(name = "ACTV_REC_IND")
    private String activeRecordIndicator;

    @MapsId("sellerId")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLR_ID",referencedColumnName = "SLR_ID")
    @JsonBackReference
    private List<SellerEntity> sellerEntities;

    public ProductIdentity getProductIdentity() {
        return productIdentity;
    }

    public void setProductIdentity(ProductIdentity productIdentity) {
        this.productIdentity = productIdentity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveRecordIndicator() {
        return activeRecordIndicator;
    }

    public void setActiveRecordIndicator(String activeRecordIndicator) {
        this.activeRecordIndicator = activeRecordIndicator;
    }

    public List<SellerEntity> getSellerEntities() {
        return sellerEntities;
    }

    public void setSellerEntities(List<SellerEntity> sellerEntities) {
        this.sellerEntities = sellerEntities;
    }
}
