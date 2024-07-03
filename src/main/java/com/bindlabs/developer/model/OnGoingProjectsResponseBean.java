package com.bindlabs.developer.model;

import java.math.BigInteger;
import java.util.Date;

import com.bindlabs.core.enums.YesOrNo;

import lombok.Data;

@Data
public class OnGoingProjectsResponseBean {

	private BigInteger developerId;
	private String reraId;
	private String projectName;
	private String location;
	private String typeOfDevelopment;
	private String companyName;
	private String structureOfBuilding;
	private String totalCarpetAreaSqFt;
	private String developerShareSqFt;
	private Integer noOfUnits;
	private Integer developerUnits;	
	private Double projectCost;
	private Double costIncurredTillDate;
	private Double costToBeIncurred;
	private Integer unitsSold;
	private String areaSold;
	private String areaUnsold;
	private Double soldUnitsAmounts;
	private Double balanceSoldUnitAmounts;
	private Double unsoldUnitsAmounts;	
	private Double salePricePerSqFtForUnsoldUnit;
	private Double expectedRentalPerSqFtPerMonth;
	private Double totalReceivableAmt;
	private Double freeCashFlow;
	private Double totalSaleValue;
	private Double debtOutstanding;
	private Double profit;	
	private Double totalDebtInCrores;
	private String stageOfConstruction;
	private Date startDate;
	private Date expectedCompletionDate;
	private YesOrNo isConstructionFinance;
	private String nameOfFinancialInstitution;
	private Integer sequence;

}
