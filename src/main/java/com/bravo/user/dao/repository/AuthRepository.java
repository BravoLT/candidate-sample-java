package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is used for the database  interactions using the Auth Entity
 */
public interface AuthRepository extends JpaRepository<Auth, String> {

    Auth findByEmailIgnoreCase(String email);
}
