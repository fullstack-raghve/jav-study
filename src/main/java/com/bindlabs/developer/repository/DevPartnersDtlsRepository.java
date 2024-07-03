package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevPartnersDtls;
import com.bindlabs.developer.entity.DevPartnersDtlsId;

@Repository
public interface DevPartnersDtlsRepository extends JpaRepository<DevPartnersDtls, DevPartnersDtlsId> {

	@Query(nativeQuery = true,value="select COUNT(*) FROM DEV_PARTNERS_DTLS where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevPartnersDtls where developerId=:developerId")
	List<DevPartnersDtls> getAllById(BigInteger developerId);

}
