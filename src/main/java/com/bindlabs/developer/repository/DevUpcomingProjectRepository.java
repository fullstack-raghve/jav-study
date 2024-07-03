package com.bindlabs.developer.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevUpcomingProject;
import com.bindlabs.developer.entity.DevUpcomingProjectId;

@Repository
public interface DevUpcomingProjectRepository extends JpaRepository<DevUpcomingProject, DevUpcomingProjectId> {

	@Query(nativeQuery = true, value = "select COUNT(*) FROM DEV_UPCOMING_PROJECT where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevUpcomingProject where developerId=:developerId")
	List<DevUpcomingProject> getAllById(BigInteger developerId);

}
