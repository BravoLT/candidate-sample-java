package com.bravo.user.model.filter;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AddressFilter {
    private Set<String> userId;
}
