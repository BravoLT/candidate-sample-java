package com.bravo.user.dao.specification;

import com.bravo.common.dao.specification.AbstractSpecification;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserFilter;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  }
}
