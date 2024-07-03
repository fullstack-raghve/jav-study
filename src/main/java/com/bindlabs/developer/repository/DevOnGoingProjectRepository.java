package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevOnGoingProject;
import com.bindlabs.developer.entity.DevOnGoingProjectId;

@Repository
public interface DevOnGoingProjectRepository extends JpaRepository<DevOnGoingProject, DevOnGoingProjectId> {

	@Query(nativeQuery = true, value = "select COUNT(*) FROM DEV_ON_GOING_PROJECT where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevOnGoingProject where developerId=:developerId")
	List<DevOnGoingProject> getAllById(@Param("developerId")BigInteger developerId);

}
