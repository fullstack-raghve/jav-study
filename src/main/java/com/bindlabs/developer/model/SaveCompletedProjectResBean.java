package com.bindlabs.developer.model;

import lombok.Data;

@Data
public class SaveCompletedProjectResBean {

	
	private String projectName;
	private String companyName;
	private String location;
	private String typeOfProject;
	private double saleValuePerSq;
	private String commencementYear;
	private String completionYear;
	
}
