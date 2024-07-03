package com.bindlabs.developer.model;

import lombok.Data;

@Data
public class SaveGroupDbtCashFlowsResBean {

	private String institutionName;
	private String facilityType;
	private String borrowingEntity;
	private Double projectSecurity;
	private Double interestRate;
	private Double sanctionAmount;
	private Double disbursed;
	private Double currentOutstanding;

}
