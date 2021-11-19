package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Address;
import com.bravo.user.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>, JpaSpecificationExecutor<User>{

    List<Address> findByUserId(String userId);
}
