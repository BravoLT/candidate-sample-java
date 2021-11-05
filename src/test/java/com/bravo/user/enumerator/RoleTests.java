package com.bravo.user.enumerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTests {

  @Test
  void findRoleExpectsNullOnNull() {
    Role expected = null;
    Role actual = Role.findRole(null);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void findRoleExpectsNullOnInvalidRole() {
    Role expected = null;
    Role actual = Role.findRole("InvalidRole");
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void findRoleFindRoleForEveryRole() {
    boolean foundEveryRole = true;
    
    for (Role role : Role.values()) {
      Role foundRole = Role.findRole(role.name());
      if (foundRole == null) {
        foundEveryRole = false;
        break;
      }
    }
    
    Assertions.assertTrue(foundEveryRole, "Failed to Find Role using findRole method");
  }

}
