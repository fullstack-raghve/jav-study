package com.bindlabs.developer.entity;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "DEV_COMPLETE_PROJECT")
@IdClass(DevCompleteProjectId.class)
public class DevCompleteProject {

	@Id
	@Column(name = "DEVELOPER_ID")
	private BigInteger developerId;

	@Id
	@Column(name = "SEQUENCE")
	private int sequence;

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

	@Column(name = "RERA_ID")
	private String reraId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FIRM_NAME")
	private String firmName;

	@Column(name = "JV_PERCENTAGE")
	private String jvPercentage;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "PROJECT_TYPE")
	private String projectType;

	@Column(name = "SALEABLE_AREA")
	private String saleableArea;

	@Column(name = "NO_OF_UNITS")
	private Integer noOfUnits;

	@Column(name = "COST")
	private Double cost;

	@Column(name = "SALES_VALUE")
	private Double salesValue;

	@Column(name = "COMMENCEMENT_YEAR")
	private String commencementYear;

	@Column(name = "COMPLETION_YEAR")
	private String completionYear;

	@Column(name = "IS_OC_RECIEVED")
	private YesOrNo isOcReceived;

	@Column(name = "UNSOLD_UNITS")
	private Integer unsoldUnits;

	@Column(name = "BANK_LOAN")
	private Double bankLoan;

}
