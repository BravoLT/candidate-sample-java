package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> {

    // TODO Users instead uses findAll with a specification and filter.
    //If we need to filter on more than just user id then this should too
    //But for now this should work and be simpler.
    Page<Payment> findByUserId(String userID, Pageable pageRequest);
}
