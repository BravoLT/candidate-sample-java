package com.bravo.user.dao.repository;

import com.bravo.user.dao.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address> {
}
