package com.bravo.user.dao.repository;

import java.util.Optional;

import com.bravo.user.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{

  Optional<User> findByEmail(String email);

}
