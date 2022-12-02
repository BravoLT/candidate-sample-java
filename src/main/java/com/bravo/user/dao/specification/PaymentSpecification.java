package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.model.filter.PaymentFilter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Set;

public class PaymentSpecification extends AbstractSpecification<Payment> {

    private final PaymentFilter filter;

    public PaymentSpecification(final PaymentFilter filter){
        this.filter = filter;
    }

    @Override
    public void doFilter(
            Root<Payment> root,
            CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder
    ){
        applyStringFilterToFields(Set.of(root.get("userId")), filter.getUserId());
    }
}
