package com.bravo.user.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bravo.user.dao.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

	List<Payment> findByUserId(final String userId);

}
