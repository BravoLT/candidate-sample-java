package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.Address;
import com.bravo.user.model.filter.AddressFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class AddressSpecification extends AbstractSpecification<Address> {
    private final AddressFilter filter;

    @Override
    void doFilter(Root<Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        applyStringFilter(root.get("userId"), filter.getUserId());
    }
}
