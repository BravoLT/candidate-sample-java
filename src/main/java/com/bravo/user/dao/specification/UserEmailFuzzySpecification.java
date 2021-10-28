/* (C)2021 */
package com.bravo.user.dao.specification;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.filter.UserEmailFuzzyFilter;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserEmailFuzzySpecification extends AbstractSpecification<User> {

    private final UserEmailFuzzyFilter filter;

    public UserEmailFuzzySpecification(final UserEmailFuzzyFilter filter) {
        this.filter = filter;
    }

    @Override
    public void doFilter(
            Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        applyStringFilterToFields(Set.of(root.get("email")), filter.getEmail());
    }
}
