package com.bravo.user.model.filter;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class UserFilter {

  public Set<String> getIds() {
    return ids;
  }

  private Set<String> ids;
  private Set<String> firstNames;
  private Set<String> lastNames;
  private Set<String> middleNames;
  private Set<Integer> phoneNumbers;

  public Set<String> getFirstNames() {
    return firstNames;
  }

  public Set<String> getLastNames() {
    return lastNames;
  }

  public Set<String> getMiddleNames() {
    return middleNames;
  }

  public Set<Integer> getPhoneNumbers() {
    return phoneNumbers;
  }

  public Integer getPhoneNumberLength() {
    return phoneNumberLength;
  }

  public DateFilter<LocalDateTime> getDateFilter() {
    return dateFilter;
  }

  private final Integer phoneNumberLength = 10;
  private DateFilter<LocalDateTime> dateFilter;
}
