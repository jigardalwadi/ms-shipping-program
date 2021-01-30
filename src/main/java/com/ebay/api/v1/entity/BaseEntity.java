package com.ebay.api.v1.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "ADD_ID", updatable = false, nullable = false)
    private String addId;

    @Column(name = "UPD_ID", nullable = false)
    private String updateId;

    @Column(name = "ADD_TMST", updatable = false, nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime addTimestamp;

    @Column(name = "UPD_TMST", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTimestamp;

    @PrePersist
    void onCreate(){
        this.setAddTimestamp(LocalDateTime.now());
        this.setUpdateTimestamp(this.getAddTimestamp());
    }

    @PreUpdate
    void onPersist(){
        this.setUpdateTimestamp(LocalDateTime.now());
    }


    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public LocalDateTime getAddTimestamp() {
        return addTimestamp;
    }

    public void setAddTimestamp(LocalDateTime addTimestamp) {
        this.addTimestamp = addTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
