package com.bindlabs.developer.model;

import lombok.Data;

@Data
public class SaveOnGoingProjectResBean {

	private String projectName;
	private String location;
	private String companyName;
	private String typeOfProject;
	private String totalCarpetAreaSqFt;
	private String developerShareSqFt;
	private Integer noOfUnits;
	private String totalSaleValue;

}
