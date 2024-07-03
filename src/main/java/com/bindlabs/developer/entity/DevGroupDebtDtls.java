package com.bindlabs.developer.entity;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bindlabs.core.enums.StatusType;

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
@Table(name = "DEV_GROUP_DEBT_DTLS")
@IdClass(DevGroupDebtDtlsId.class)
public class DevGroupDebtDtls {

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

	@Column(name = "NAME")
	private String name;

	@Column(name = "FACILITY_TYPE")
	private String facilityType;

	@Column(name = "BORROWING_ENTITY")
	private String borrowingEntity;

	@Column(name = "PROJECT_SECURITY")
	private Double projectSecurity;

	@Column(name = "INTEREST_RATE")
	private Double interestRate;

	@Column(name = "SANCTION_AMOUNT")
	private Double sanctionAmount;

	@Column(name = "SANCTION_DATE")
	private Date sanctionDate;

	@Column(name = "SANCTION_LIMITS")
	private Double sanctionLimits;

	@Column(name = "DISBURSED")
	private Double disbursed;

	@Column(name = "DISBURSEMENT_DATE")
	private Date disbursementDate;

	@Column(name = "TOBE_DISBURSED")
	private Double tobeDisbursed;

	@Column(name = "CURRENT_OUTSTANDING")
	private Double currentOutstanding;

	@Column(name = "REPAYMENT_START_DATE")
	private Date repaymentStartDate;

	@Column(name = "TENURE")
	private Integer tenure;

	@Column(name = "REVISED_TENURE")
	private Integer revisedTenure;

	@Column(name = "LOAN_END_DATE")
	private Date loanEndDate;

	@Column(name = "SANCTION_LETTER_PATH")
	private String sanctionLetterPath;

	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;

}
