package com.raviv.coupons.beans;

import java.sql.Timestamp;

/**
 * Parent Java bean, all java beans will extend it. includes technical attributes
 * @author raviv
 *
 */
public abstract class InfraBean {
	
	/*
	 * SYS_CREATION_DATE	datetime			not null,
	 * SYS_UPDATE_DATE		datetime			not null,
	 * CREATED_BY_USER_ID	int					not null,
	 * UPDATED_BY_USER_ID	int					not null, 
	 */
	
	private long   		sysCreationDate;
	private long   		sysUpdateDate;
	private int   		createdByUserId;
	private int   		updatedByUserId;
	
	private Timestamp   sysCreationDateTimeStamp; 	// We will use this for printing, more elegant than ugly long ...
	private Timestamp   sysUpdateDateTimeStamp;		// We will use this for printing, more elegant than ugly long ...
	
	
	
	public InfraBean() {
		//super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Control\t[createdByUserId=" + createdByUserId + 
				", updatedByUserId=" + updatedByUserId + 
				", sysCreationDateTimeStamp=" + sysCreationDateTimeStamp + 
				", sysUpdateDateTimeStamp=" + sysUpdateDateTimeStamp + "]\n";
	}

	
	public long getSysCreationDate() {
		return sysCreationDate;
	}
	public void setSysCreationDate(long sysCreationDate) {
		this.sysCreationDate = sysCreationDate;
		this.sysCreationDateTimeStamp = new Timestamp(sysCreationDate);
	}
	public long getSysUpdateDate() {
		return sysUpdateDate;
	}
	public void setSysUpdateDate(long sysUpdateDate) {
		this.sysUpdateDate = sysUpdateDate;
		this.sysUpdateDateTimeStamp = new Timestamp(sysUpdateDate);
	}
	public int 	getCreatedByUserId() {
		return createdByUserId;
	}
	public void setCreatedByUserId(int createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	public int 	getUpdatedByUserId() {
		return updatedByUserId;
	}
	public void setUpdatedByUserId(int updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	
	
	
}
