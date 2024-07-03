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
@Table(name = "DEV_LANDHOLDING_DTLS")
@IdClass(DevLandholdingDtlsId.class)
public class DevLandholdingDtls {

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

	@Column(name = "NAME")
	private String name;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "LAND_USE")
	private String landUse;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "AREA")
	private String area;

	@Column(name = "BOOK_VALUE")
	private Double bookValue;

	@Column(name = "MARKET_VALUE")
	private Double marketValue;

	@Column(name = "PURCHASE_YEAR")
	private Date purchaseYear;

	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;

}
