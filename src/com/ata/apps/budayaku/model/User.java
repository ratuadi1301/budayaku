package com.ata.apps.budayaku.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sys_user")
public class User extends ModelEntity{

	private static final long serialVersionUID = 1L;

	public static final String USERNAME = "username";
	public static final String PWD_DIGEST = "pwdDigest";
	public static final String FULL_NAME = "fullName";
	public static final String GROUP = "group";
	public static final String USER_SESSION = "userSession";
	public static final String LAST_LOGIN_DATE = "lastLoginDate";
	public static final String LAST_LOGIN_ADDRESS = "lastLoginAddress";
	public static final String PWD_CREATE_DATE = "pwdCreateDate";
	public static final String PWD_END_DATE = "pwdEndDate";
	public static final String WRONG_PWD_COUNT = "wrongPwdCount";
	public static final String PASSWORD_EXPIRED = "passwordExpired";
	public static final String LOCKED = "locked";
	public static final String DISABLED = "disabled";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 20, nullable = false, unique = true)
	private String username;

	@Column(length = 64, nullable = false)
	private String pwdDigest;

	@Column(length = 50, nullable = false)
	private String fullName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@Column(length = 128)
	private String userSession;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	@Column(length = 100)
	private String lastLoginAddress;

	@Temporal(TemporalType.DATE)
	private Date pwdCreateDate;

	@Temporal(TemporalType.DATE)
	private Date pwdEndDate;

	@Column(nullable = false)
	private int wrongPwdCount = 0;

	@Column(nullable = false)
	private boolean passwordExpired = false;

	@Column(nullable = false)
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
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


	public boolean isSystemAdmin() {
		return group.isSysAdminGroup();
	}
	
}
