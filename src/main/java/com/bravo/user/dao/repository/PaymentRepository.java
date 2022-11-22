package com.bravo.user.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bravo.user.dao.model.Payment;

/**
 * Payment Repository
 * 
 * @author Bob Wilson
 * Created November 2022
 * 
 */
public interface PaymentRepository extends JpaRepository<Payment, String> {
	
	/* 
	 * Find payments by userId
	 * 
	 * @param userId
	 * @return a list of Payment objects.
	 */
	List<Payment> findByUserId(final String userId);

}
