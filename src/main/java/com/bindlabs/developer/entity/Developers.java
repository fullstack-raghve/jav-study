package com.bindlabs.developer.entity;

import java.math.BigInteger;

import com.bindlabs.core.entity.BaseEntity;
import com.bindlabs.core.enums.ApprovalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DEVELOPERS")
public class Developers extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEVELOPER_ID")
	private BigInteger developerId;

	@Column(name = "APPROVAL_STATUS")
	private ApprovalStatus approvalStatus;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FORMATION_YEAR")
	private Integer formationYear;

	@Column(name = "BUSINESS_NATURE")
	private String businessNature;

	@Column(name = "WEBSITE_URL")
	private String websiteUrl;

	@Column(name = "PAN_NUMBER")
	private String panNumber;

	@Column(name = "CIN_NUMBER")
	private String cinNumber;

	@Column(name = "GSTIN_NUMBER")
	private String gstinNumber;

	@Column(name = "KYC_DOC_PATH")
	private String kycDocPath;

	@Column(name = "INCORPORATION_DOC_PATH")
	private String incorporationDocPath;

	@Column(name = "FINANCIAL_DOC_PATH")
	private String financialDocPath;

	@Column(name = "ORG_STRUCTURE_DOC_PATH")
	private String orgStructureDocPath;

	@Column(name = "CASH_FLOWS_DOC_PATH")
	private String cashFlowsDocPath;

	@Column(name = "ABOUT")
	private String about;

}
