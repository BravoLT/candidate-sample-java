package com.bravo.user.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bravo.user.dao.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

	List<Address> findByUserId(final String userId);

}