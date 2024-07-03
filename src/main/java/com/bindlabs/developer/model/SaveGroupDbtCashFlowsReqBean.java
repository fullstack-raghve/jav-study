package com.bindlabs.developer.model;

import java.util.Date;

import lombok.Data;

@Data
public class SaveGroupDbtCashFlowsReqBean {

	private String developerId;
	private String institutionName;
	private String facilityType;
	private String borrowingEntity;
	private Double projectSecurity;
	private Double interestRate;
	private Double sanctionAmount;
	private Date sanctionDate;
	private Double sanctionLimits;
	private Double disbursed;
	private Date disbursementDate;
	private Double tobeDisbursed;
	private Double currentOutstanding;
	private Date repaymentStartDate;
	private Integer tenure;
	private Integer revisedTenure;
	private Date loanEndDate;
	private String sanctionLetterPath;
	private Integer sequence;

}
