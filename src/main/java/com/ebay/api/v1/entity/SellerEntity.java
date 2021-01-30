package com.ebay.api.v1.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SLR_ENTITY")
public class SellerEntity extends BaseEntity{

    @Id
    @Column(name = "SLR_ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @Column(name = "SLR_NAME", nullable = false)
    private String sellerName;

    @Column(name = "SLR_EMAIL", nullable = false,unique = true)
    private String sellerEmail;

    @Column(name = "ACTV_REC_IND")
    private String activeRecordIndicator;

    @OneToMany(mappedBy = "sellerId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PersonEnrollmentEntity> personEnrollments;

    @ManyToMany(mappedBy = "sellerEntities",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductEntity> productEntities;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getActiveRecordIndicator() {
        return activeRecordIndicator;
    }

    public void setActiveRecordIndicator(String activeRecordIndicator) {
        this.activeRecordIndicator = activeRecordIndicator;
    }

    public List<PersonEnrollmentEntity> getPersonEnrollments() {
        return personEnrollments;
    }

    public void setPersonEnrollments(List<PersonEnrollmentEntity> personEnrollments) {
        this.personEnrollments = personEnrollments;
    }

    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }
}
