package com.bravo.user.model.filter;

import java.util.Set;

import lombok.Data;

@Data
public class AddressFilter {

  private Set<String> ids;
  private Set<String> zips;
}
