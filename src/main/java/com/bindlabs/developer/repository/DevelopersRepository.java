package com.bindlabs.developer.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.Developers;

@Repository
public interface DevelopersRepository extends JpaRepository<Developers, BigInteger> {

	
}
