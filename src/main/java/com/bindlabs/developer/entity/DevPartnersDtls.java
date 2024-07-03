package com.bindlabs.developer.entity;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bindlabs.core.enums.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "DEV_PARTNERS_DTLS")
@IdClass(DevPartnersDtlsId.class)
public class DevPartnersDtls {
	
	@Id
	@Column(name = "DEVELOPER_ID")
	private BigInteger developerId;

	@Id
	@Column(name = "SEQUENCE")
	private int sequence;
	
	@CreationTimestamp
	@Column(name = "CREATION_TIME", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	@UpdateTimestamp
	@Column(name = "MODIFIED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "DIN_NUMBER")
	private String dinNumber;

	@Column(name = "PAN_NUMBER")
	private String panNumber;
	
	@Column(name = "ADDRESS")
	private String address;
	
	
}
