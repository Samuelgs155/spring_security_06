package com.spring.security.app_security.repository;

import com.spring.security.app_security.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, BigInteger> {

    Optional<CustomerEntity> findByEmail(String email);
}
