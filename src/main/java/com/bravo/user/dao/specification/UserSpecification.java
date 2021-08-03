package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserFilter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Set;

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

    // Validates whether phone number contains only digits
    applyInFilter(root.get("phoneNumber"), filter.getPhoneNumbers());

    // Validates whether phone number complies with correct length
    applyInLengthFilter(root.get("phoneNumber"), filter.getPhoneNumbers(), filter.getPhoneNumberLength());

  }


}
