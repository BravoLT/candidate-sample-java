package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserFilter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

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

    Expression<String> firstNameExpr = root.get("firstName");
    Expression<String> lastNameExpr = root.get("lastName");
    Expression<String> middleNameExpr = root.get("middleName");
    applyStringFilter(firstNameExpr, filter.getFirstNames());
    applyStringFilter(lastNameExpr, filter.getLastNames());
    applyStringFilter(middleNameExpr, filter.getMiddleNames());
    
    // add ability to filter by combined first, middle, and last names
    Expression<String> fullNameExpr = criteriaBuilder.concat(
    		getNameOrEmptyIfNull(criteriaBuilder, firstNameExpr),
    		criteriaBuilder.concat(
    				getNameOrEmptyIfNull(criteriaBuilder, middleNameExpr),
    				getNameOrEmptyIfNull(criteriaBuilder, lastNameExpr)
    		)
    );
    applyStringFilter(fullNameExpr, filter.getFullNames());
  }
  
  private Expression<String> getNameOrEmptyIfNull(CriteriaBuilder criteriaBuilder, Expression<String> nameExpr) {
	  return criteriaBuilder.trim(criteriaBuilder.coalesce(nameExpr, ""));
  }
}
