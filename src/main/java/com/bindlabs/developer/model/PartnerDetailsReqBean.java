package com.bindlabs.developer.model;

import lombok.Data;

@Data
public class PartnerDetailsReqBean {

	private String developerId;
	private Integer sequence;
	private String name;
	private String din;
	private String presentAddress;

}
