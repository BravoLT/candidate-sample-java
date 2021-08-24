package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.Address;
import com.bravo.user.model.filter.AddressFilter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AddressSpecification extends AbstractSpecification<Address> {

  private final  AddressFilter filter;

  public AddressSpecification(final AddressFilter filter){
    this.filter = filter;
  }

  @Override
  public void doFilter(Root< Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
  //  applyInFilter(root.get("id"), filter.getIds());

    applyStringFilter(root.get("zip"), filter.getZips());
  }
}
