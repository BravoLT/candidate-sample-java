package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment>{
    List<Payment> findPaymentsByUserId(final String userId);
}
