package com.bindlabs.developer.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bindlabs.developer.entity.DevCompleteProject;

@Repository
public interface DevCompleteProjectRepository extends JpaRepository<DevCompleteProject, Serializable> {

	@Query(nativeQuery = true,value="select COUNT(*) FROM DEV_COMPLETE_PROJECT where DEVELOPER_ID=?1")
	int getcount(BigInteger developerId);

	@Query("from DevCompleteProject where developerId=:developerId")
	List<DevCompleteProject> getAllById(BigInteger developerId);

}
