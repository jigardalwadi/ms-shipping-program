package com.ebay.api.v1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRSN_ENRL")
public class PersonEnrollmentEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENRL_ID",nullable = false)
    private Long enrollmentId;

    @Column(name = "ENRL_CTGR",nullable = false)
    private String enrollmentCategory;

    @Column(name = "SLR_ID",nullable = false)
    private Long sellerId;

    @MapsId("sellerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLR_ID",referencedColumnName = "SLR_ID")
    @JsonBackReference
    private SellerEntity sellerEntity;

    @Column(name = "ACTV_REC_IND")
    private String activeRecordIndicator;

    @Column(name = "ENRL_STAT")
    private String enrollmentStatus;

    @Column(name = "ENRL_USR_ID")
    private String enrollmentUser;

    @Column(name = "ENRL_TMST")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime enrollmentTimestamp;

    @Column(name = "UNENRL_USR_ID")
    private String unenrollmentUser;

    @Column(name = "UNENRL_TMST")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime unenrollmentTimestamp;

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getEnrollmentCategory() {
        return enrollmentCategory;
    }

    public void setEnrollmentCategory(String enrollmentCategory) {
        this.enrollmentCategory = enrollmentCategory;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public SellerEntity getSellerEntity() {
        return sellerEntity;
    }

    public void setSellerEntity(SellerEntity sellerEntity) {
        this.sellerEntity = sellerEntity;
    }

    public String getActiveRecordIndicator() {
        return activeRecordIndicator;
    }

    public void setActiveRecordIndicator(String activeRecordIndicator) {
        this.activeRecordIndicator = activeRecordIndicator;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEnrollmentUser() {
        return enrollmentUser;
    }

    public void setEnrollmentUser(String enrollmentUser) {
        this.enrollmentUser = enrollmentUser;
    }

    public LocalDateTime getEnrollmentTimestamp() {
        return enrollmentTimestamp;
    }

    public void setEnrollmentTimestamp(LocalDateTime enrollmentTimestamp) {
        this.enrollmentTimestamp = enrollmentTimestamp;
    }

    public String getUnenrollmentUser() {
        return unenrollmentUser;
    }

    public void setUnenrollmentUser(String unenrollmentUser) {
        this.unenrollmentUser = unenrollmentUser;
    }

    public LocalDateTime getUnenrollmentTimestamp() {
        return unenrollmentTimestamp;
    }

    public void setUnenrollmentTimestamp(LocalDateTime unenrollmentTimestamp) {
        this.unenrollmentTimestamp = unenrollmentTimestamp;
    }
}
