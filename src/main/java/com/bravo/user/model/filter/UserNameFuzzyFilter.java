package com.bravo.user.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNameFuzzyFilter implements Serializable {
  @Serial
  private static final long serialVersionUID = 2405172041950251807L;
  private String name;
}
