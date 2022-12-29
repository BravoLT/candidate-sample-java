package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.Payment;

import java.util.Collections;

import javax.persistence.criteria.*;

public class PaymentSpecification extends AbstractSpecification<Payment> {

  private final String userId;

  public PaymentSpecification(final String userId){
    this.userId = userId;
  }

  @Override
  public void doFilter(
      Root<Payment> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ){
    applyStringFilter(root.get("userId"), Collections.singletonList(userId));
  }
}
