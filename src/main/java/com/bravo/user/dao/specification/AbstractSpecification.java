package com.bravo.user.dao.specification;

import com.bravo.user.model.filter.DateFilter;
import com.bravo.user.utility.ValidatorUtil;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSpecification<T> implements Specification<T> {

  private CriteriaBuilder criteriaBuilder;
  private List<Predicate> predicates;

  @Override
  public Predicate toPredicate(
      Root<T> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ) {
    this.criteriaBuilder = criteriaBuilder;
    this.predicates = new ArrayList<>();

    doFilter(root, criteriaQuery, criteriaBuilder);

    return criteriaQuery
        .distinct(true)
        .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
        .getRestriction();
  }

  abstract void doFilter(
      Root<T> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  );

  protected void applyDateTimeFilter(
      final Expression<LocalDateTime> path,
      final DateFilter<LocalDateTime> value
  ){
    if(ValidatorUtil.isInvalid(value)){
      return;
    }
    final LocalDateTime now = LocalDateTime.now();
    final LocalDateTime start = value.getStartOrDefault(now.minus(1, ChronoUnit.CENTURIES));
    final LocalDateTime until = value.getUntilOrDefault(now);

    if(ValidatorUtil.isValid(start)) {
      predicates.add(criteriaBuilder.between(path, start, until));
    }
  }

  protected <X, Y extends Collection<X>> void applyInFilter(
      final Expression<X> path,
      final Y values
  ){
    if(ValidatorUtil.isInvalid(values)){
      return;
    }
    predicates.add(path.in(values));
  }

  protected <X, Y  extends Collection<X>> void applyInLengthFilter(
          final Expression<X> path,
          final Y values,
          final Integer length
  ){
    if(ValidatorUtil.isInvalidLength(values, length)){
      return;
    }
    predicates.add(path.in(values));
  }

  protected  <X extends Collection<String>> void applyStringFilter(
      final Expression<String> path,
      final X values
  ){
    if(ValidatorUtil.isInvalid(values)){
      return;
    }
    final Expression<String> targetPath = criteriaBuilder.lower(path);
    final List<String> inClause = new ArrayList<>();

    for(String s : values){
      final boolean isLike = s.contains("%") || s.contains("*");
      final boolean isNot = s.startsWith("!");

      if(!isLike && !isNot){
        inClause.add(s.toLowerCase());
        continue;
      }
      final String targetValue = (isNot ? s.substring(1) : s).toLowerCase();
      Predicate predicate;
      if(isLike){
        predicate = criteriaBuilder.like(targetPath, targetValue);
      } else {
        predicate = criteriaBuilder.equal(targetPath, targetValue);
      }

      if(isNot){
        predicate = criteriaBuilder.not(predicate);
      }
      predicates.add(predicate);
    }
    if(!inClause.isEmpty()){
      applyInFilter(targetPath, inClause);
    }
  }

}
