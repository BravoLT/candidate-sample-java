package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserNameFuzzyFilter;


import java.util.Set;


import jakarta.persistence.criteria.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class UserNameFuzzySpecification extends AbstractSpecification<User> {

  private final UserNameFuzzyFilter filter;

  public UserNameFuzzySpecification(final UserNameFuzzyFilter filter) {
    this.filter = filter;
  }

  @Override
  public void doFilter(
      Root<User> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ) {
    applyStringFilterToFields(Set.of(
        root.get("firstName"),
        root.get("lastName"),
        root.get("middleName")
    ), filter.getName());
  }

}
