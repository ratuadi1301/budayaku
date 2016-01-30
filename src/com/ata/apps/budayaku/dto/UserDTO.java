package com.ata.apps.budayaku.dto;

import java.util.Date;

public class UserDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String pwdDigest;
	private String fullName;
	private GroupDTO group;
	private String userSession;
	private Date lastLoginDate;
	private String lastLoginAddress;
	private Date pwdCreateDate;
	private Date pwdEndDate;
	private int wrongPwdCount = 0;
	private boolean passwordExpired = false;
	private boolean locked = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwdDigest() {
		return pwdDigest;
	}

	public void setPwdDigest(String pwdDigest) {
		this.pwdDigest = pwdDigest;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public GroupDTO getGroup() {
		return group;
	}

	public void setGroup(GroupDTO group) {
		this.group = group;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	public Date getPwdCreateDate() {
		return pwdCreateDate;
	}

	public void setPwdCreateDate(Date pwdCreateDate) {
		this.pwdCreateDate = pwdCreateDate;
	}

	public Date getPwdEndDate() {
		return pwdEndDate;
	}

	public void setPwdEndDate(Date pwdEndDate) {
		this.pwdEndDate = pwdEndDate;
	}

	public int getWrongPwdCount() {
		return wrongPwdCount;
	}

	public void setWrongPwdCount(int wrongPwdCount) {
		this.wrongPwdCount = wrongPwdCount;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}


}
