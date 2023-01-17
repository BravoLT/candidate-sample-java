package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.model.filter.PaymentFilter;
import lombok.EqualsAndHashCode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.Collections;
import javax.persistence.criteria.*;

@EqualsAndHashCode
public class PaymentSpecification extends AbstractSpecification<Payment> {

    private final PaymentFilter filter;

    public PaymentSpecification(final PaymentFilter filter) {
        this.filter = filter;
    }

    @Override
    void doFilter(
            Root<Payment> root,
            CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder
    ) {
        applyStringFilterToFields(Collections.singletonList(
                root.get("userId")
        ), filter.getUserId());
    }
}
