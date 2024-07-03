package com.bindlabs.developer.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PartnerDetailsResponseBean {

	private Integer sequence;
	private String name;
    private String din;
    private String panNo;
    private String presentAddress;
    private BigInteger developerId;

}
