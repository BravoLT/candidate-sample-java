package com.bravo.user.model.filter;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilter {

  private Set<String> ids;
  private Set<String> firstNames;
  private Set<String> lastNames;
  private Set<String> middleNames;
  private DateFilter<LocalDateTime> dateFilter;
	private Set<String> emails;

}
