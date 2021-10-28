package com.bravo.user.model.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEmailFuzzyFilter {

  private String email;
}
