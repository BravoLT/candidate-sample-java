package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    ArrayList<Payment> findAllByUserId(String userId);
}
