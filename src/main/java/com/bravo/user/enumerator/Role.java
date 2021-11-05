package com.bravo.user.enumerator;

public enum Role {
  ADMIN;

  /**
   * Returns matching Role enumeration without throwing a null pointer exception
   * if one is not found
   * 
   * @param value
   * @return
   */
  public static Role findRole(String value) {

    /*
     * This being the likely scenario I think it makes sense to short circuit
     */
    if (value == null) {
      return null;
    }

    Role foundRole = null;

    for (Role role : Role.values()) {
      if (role.name().equals(value)) {
        /*
         * Opted for an assign / break rather than return. Could have gone either way
         * really
         */
        foundRole = role;
        break;
      }
    }
    return foundRole;
  }

}
