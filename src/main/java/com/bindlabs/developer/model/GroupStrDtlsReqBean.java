package com.bindlabs.developer.model;

import java.util.List;

import lombok.Data;

@Data
public class GroupStrDtlsReqBean {

	private String name;
	private Integer yearOfFormation;
	private String natureOfBusiness;
	private String website;
	private String panNo;
	private String cinNumber;
	private String gstin;
	private List<PartnerDetails> partnersDetails;
	private String kycDocPath;
	private String incorporationDocPath;
	private String financialDocPath;
	private String orgStructureDocPath;
	private String developerId;
	private String about;

}
