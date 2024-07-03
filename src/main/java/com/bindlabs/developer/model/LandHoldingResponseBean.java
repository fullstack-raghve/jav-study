package com.bindlabs.developer.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class LandHoldingResponseBean {

	private BigInteger developerId;
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
