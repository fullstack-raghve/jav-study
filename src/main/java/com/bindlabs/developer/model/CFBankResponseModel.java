package com.bindlabs.developer.model;

import java.math.BigInteger;

import com.bindlabs.core.enums.BankOnBoardStatus;
import com.bindlabs.core.enums.InstitutionType;

import lombok.Data;

@Data
public class CFBankResponseModel {

	private BigInteger financialInstitutionId;
	private BankOnBoardStatus status;
	private String name;
	private String logoPath;
	private InstitutionType institutionType;
	private String email;
	private String website;
	private String contactInfo;

}
