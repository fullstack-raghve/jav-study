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
@Table(name = "DEV_ON_GOING_PROJECT")
@IdClass(DevOnGoingProjectId.class)
public class DevOnGoingProject {

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

	@Column(name = "RERA_ID")
	private String reraId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "DEVELOPMENT_TYPE")
	private String developmentType;

	@Column(name = "FIRM_NAME")
	private String firmName;

	@Column(name = "BUILDING_STRUCTURE")
	private String buildingStructure;

	@Column(name = "TOTAL_CARPET_AREA")
	private String totalCarpetArea;

	@Column(name = "DEVELOPER_SHARES")
	private String developerShares;

	@Column(name = "NO_OF_UNITS")
	private Integer noOfUnits;

	@Column(name = "DEVELOPER_UNITS")
	private Integer developerUnits;

	@Column(name = "PROJECT_COST")
	private Double projectCost;

	@Column(name = "TILL_COST_INCURRED")
	private Double tillCostIncurred;

	@Column(name = "TOBE_COST_INCURRED")
	private Double tobeCostIncurred;

	@Column(name = "UNITS_SOLD")
	private Integer unitsSold;

	@Column(name = "SOLD_AREA")
	private String soldArea;

	@Column(name = "UNSOLD_AREA")
	private String unsoldArea;

	@Column(name = "SOLD_UNITS_AMOUNT")
	private Double soldUnitsAmount;

	@Column(name = "BALANCE_SOLD_UNITS_AMT")
	private Double balanceSoldUnitsAmt;

	@Column(name = "UNSOLD_UNITS_AMOUNT")
	private Double unsoldUnitsAmount;

	@Column(name = "UNSOLD_UNITS_SALE_PRICE")
	private Double unsoldUnitsSalePrice;

	@Column(name = "EXPECTED_RENT_PER_MONTH")
	private Double expectedRentPerMonth;

	@Column(name = "TOTAL_RECEIVABLE_AMT")
	private Double totalReceivableAmt;

	@Column(name = "FREE_CASH_FLOW")
	private Double freeCashFlow;

	@Column(name = "TOTAL_SALE_VALUE")
	private Double totalSaleValue;

	@Column(name = "DEBT_OUTSTANDING")
	private Double debtOutstanding;

	@Column(name = "PROFIT")
	private Double profit;

	@Column(name = "TOTAL_DEBT_IN_CR")
	private Double totalDebtInCr;

	@Column(name = "CONSTRUCTION_STAGE")
	private String constructionStage;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "COMPLETION_DATE")
	private Date completionDate;

	@Column(name = "IS_CONSTRUCTION_FINANCE")
	private YesOrNo isConstructionFinance;

	@Column(name = "FINANCIAL_INSTITUTION_NAME")
	private String financialInstitutionName;

}