package com.ebay.api.v1.repository;

import com.ebay.api.v1.entity.PersonEnrollmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonEnrollmentRepository extends CrudRepository<PersonEnrollmentEntity, Long> {

    @Query("SELECT pe FROM PersonEnrollmentEntity pe WHERE pe.sellerId =?1 AND pe.enrollmentCategory = ?2")
    PersonEnrollmentEntity getEnrollmentRecords(Long sellerId, String shippingProgram);
}
