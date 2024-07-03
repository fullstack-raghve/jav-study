package com.bindlabs.developer.entity;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevOnGoingProjectId implements Serializable {

    private static final long serialVersionUID = 3953377140618310246L;

    private BigInteger developerId;

    private Integer sequence;

}
