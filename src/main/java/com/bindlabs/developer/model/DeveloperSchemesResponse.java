package com.bindlabs.developer.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class DeveloperSchemesResponse {

	private BigInteger schemeId;
	private String schemeName;
	private String availabilityDate;
	private List<DeveloperSchemesDetailsResponse> schemesDetails;

}
