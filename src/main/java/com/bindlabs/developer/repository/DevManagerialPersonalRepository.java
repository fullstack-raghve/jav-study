package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevManagerialPersonal;
import com.bindlabs.developer.entity.DevManagerialPersonalId;

@Repository
public interface DevManagerialPersonalRepository extends JpaRepository<DevManagerialPersonal, DevManagerialPersonalId> {

	@Query(nativeQuery = true,value="select COUNT(*) FROM DEV_MANAGERIAL_PERSONAL where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevManagerialPersonal where developerId=:developerId")
	List<DevManagerialPersonal> getAllById(BigInteger developerId);

}
