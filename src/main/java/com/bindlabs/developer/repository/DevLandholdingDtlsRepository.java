package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevLandholdingDtls;
import com.bindlabs.developer.entity.DevLandholdingDtlsId;

@Repository
public interface DevLandholdingDtlsRepository extends JpaRepository<DevLandholdingDtls, DevLandholdingDtlsId> {

	@Query(nativeQuery = true, value = "select COUNT(*) FROM DEV_LANDHOLDING_DTLS where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevLandholdingDtls where developerId=:developerId")
	List<DevLandholdingDtls> getAllById(BigInteger developerId);

}
