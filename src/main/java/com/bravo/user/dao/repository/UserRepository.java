package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String>, JpaSpecificationExecutor<User>{
    Optional<User> findByEmail(final String email);
}
