package com.bravo.user.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserFilter;

public class UserSpecification extends AbstractSpecification<User> {

  private final UserFilter filter;

  public UserSpecification(final UserFilter filter){
    this.filter = filter;
  }

  @Override
  public void doFilter(
      Root<User> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ){
    applyDateTimeFilter(root.get("updated"), filter.getDateFilter());

    applyInFilter(root.get("id"), filter.getIds());

    applyStringFilter(root.get("firstName"), filter.getFirstNames());
    applyStringFilter(root.get("lastName"), filter.getLastNames());
    applyStringFilter(root.get("middleName"), filter.getMiddleNames());

	applyStringFilter(root.get("email"), filter.getEmails());
  }

}
