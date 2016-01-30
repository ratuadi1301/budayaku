package com.ata.apps.budayaku.dto;

import java.io.Serializable;
import java.util.Date;

public abstract class DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ID = "id";
	public static final String CREATED_TIME = "createdTime";
	public static final String MODIFIED_TIME = "modifiedTime";
	public static final String VERSION = "version";
	public static final String DISABLED = "disabled";

	private Long id;
	private Date createdTime;
	private Date modifiedTime;
	private String version;
	private boolean disabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id).append(", createdTime: ")
				.append(createdTime).append(", modifiedTime: " + modifiedTime)
				.append(", version: " + version)
				.append(", disabled: " + disabled);
		return sb.toString();
	}

}
