package com.bindlabs.developer.model;

import java.util.Date;

import lombok.Data;

@Data
public class SaveUpcomingProjectReqBean {

	private String developerId;
	private String projectName;
	private String companyName;
	private String location;
	private String typeOfProject;
	private String projectIsUnderJv;
	private String jvPartnerShare;
	private String totalSaleableArea;
	private Double totalCost;
	private Double totalProjectSalesValue;
	private Double profit;
	private Date proposedStartDate;
	private Integer sequence;

}
