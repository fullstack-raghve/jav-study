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
@Table(name = "DEV_UPCOMING_PROJECT")
@IdClass(DevUpcomingProjectId.class)
public class DevUpcomingProject {

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

    @Column(name = "FIRM_NAME")
    private String firmName;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PROJECT_TYPE")
    private String projectType;

    @Column(name = "PROJECT_UNDER_JV")
    private String projectUnderJV;

    @Column(name = "JV_PARTNER_SHARE")
    private String jvPartnerShare;

    @Column(name = "TOTAL_SALEABLE_AREA")
    private String totalSaleableArea;

    @Column(name = "TOTAL_COST" )
    private Double totalCost;

    @Column(name = "TOTAL_PROJECT_VALUE" )
    private Double totalProjectValue;

    @Column(name = "PROFIT" )
    private Double profit;

    @Column(name = "START_DATE")
    private Date startDate;

}