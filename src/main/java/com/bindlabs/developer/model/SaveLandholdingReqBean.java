package com.bindlabs.developer.model;

import java.util.Date;

import lombok.Data;

@Data
public class SaveLandholdingReqBean {

	private String developerId;
	private Integer sequence;
	private String ownerName;
	private String projectName;
	private String landUse;
	private String location;
	private String area;
	private Double bookValue;
	private Double marketValue;
	private Date purchaseYear;

}
