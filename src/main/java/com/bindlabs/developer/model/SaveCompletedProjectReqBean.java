package com.bindlabs.developer.model;

import com.bindlabs.core.enums.YesOrNo;

import lombok.Data;

@Data
public class SaveCompletedProjectReqBean {

	private String developerId;
	private String reraId;
	private String projectName;
	private String companyName;
	private String jvPercentage;
	private String location;
	private String typeOfProject;
	private String saleableArea;
	private Integer noOfUnits;
	private Double costOfProject;
	private Double salesValuePerSq;
	private String commencementYear;
	private String completionYear;
	private YesOrNo isOcReceived;
	private Integer unsoldUnits;
	private Double bankLoan;
	private Integer sequence;

}
