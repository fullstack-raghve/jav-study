package com.bindlabs.developer.entity;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bindlabs.core.enums.EducationalQualification;
import com.bindlabs.core.enums.StatusType;
import com.bindlabs.core.enums.YesOrNo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "DEV_MANAGERIAL_PERSONAL")
@IdClass(DevManagerialPersonalId.class)
public class DevManagerialPersonal {

	@Id
	@Column(name = "DEVELOPER_ID")
	private BigInteger developerId;

	@Id
	@Column(name = "SEQUENCE")
	private Integer sequence;

	@CreationTimestamp
	@Column(name = "CREATION_TIME", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	@UpdateTimestamp
	@Column(name = "MODIFIED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;

	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "QUALIFICATION")
	private EducationalQualification qualification;

	@Column(name = "EXPERIENCE_YEAR")
	private Integer experienceYear;

	@Column(name = "AGE")
	private Integer age;

	@Column(name = "IS_SHARE_HOLDER")
	private YesOrNo isShareHolder;

	@Column(name = "SHARE_VALUE")
	private String shareValue;

	@Column(name = "FUNCTIONS_HANDLED")
	private String functionsHandler;

	@Column(name = "KYC_DOC_PATH")
	private String kycDocPath;

	@Column(name = "WRITE_DOC_PATH")
	private String writeDocPath;

}
