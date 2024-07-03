package com.bindlabs.developer.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class GroupStructureBean {

	private String name;
	private Integer yearOfFormation;
	private String natureOfBusiness;
	private String website;
	private String panNo;
	private String cinNumber;
	private String gstin;
	private String kycDocPath;
	private String incorporationDocPath;
	private String financialDocPath;
	private String orgStructureDocPath;
	private String cashFlowDocPath;
	private BigInteger developerId;

}
