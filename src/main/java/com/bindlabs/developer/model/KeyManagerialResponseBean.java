package com.bindlabs.developer.model;

import java.math.BigInteger;
import java.util.List;

import com.bindlabs.core.enums.EducationalQualification;
import com.bindlabs.core.enums.YesOrNo;

import lombok.Data;

@Data
public class KeyManagerialResponseBean {

	private String name;
	private String email;
	private EducationalQualification qualification;
	private Integer yearOfExperience;
	private Integer age;
	private List<String> functionsHandled;
	private YesOrNo isShareHolder;
	private String shareValue;
	private String kycProof;
	private String writeUp;
	private Integer sequence;
	private BigInteger developerId;

}
