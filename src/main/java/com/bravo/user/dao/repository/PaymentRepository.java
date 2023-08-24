package com.bravo.user.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bravo.user.dao.model.Payment;

public interface PaymentRepository  extends JpaRepository<Payment, String> {
    List<Payment> findByUserId(final String userId);
}
