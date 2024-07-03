package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevGroupDebtDtls;
import com.bindlabs.developer.entity.DevGroupDebtDtlsId;

@Repository
public interface DevGroupDebtDtlsRepository extends JpaRepository<DevGroupDebtDtls, DevGroupDebtDtlsId> {

	@Query(nativeQuery = true, value = "select COUNT(*) FROM DEV_GROUP_DEBT_DTLS where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevGroupDebtDtls where developerId=:developerId")
	List<DevGroupDebtDtls> getAllById(@Param("developerId")BigInteger developerId);

}
