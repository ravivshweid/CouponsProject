package com.raviv.coupons.beans;

/**
 * Java bean for Customers entity
 * @author raviv
 *
 */
public class User extends InfraBean {

	private	int                     	userId;
//	private	long                     	sysCreationDate;
//	private	long                     	sysUpdateDate;
//	private	int                      	createdByUserId;
//	private	int                      	updatedByUserId;
	private	int                      	userProfileId;
	private	String                   	userName;
	private	String                   	loginName;
	private	String                   	loginPassword;


	/**
	 * Default constructor
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * Default constructor
	 */
	public User( String userName, String loginName, String loginPwd ) {
		super();
		setUserName			( userName 	);
		setLoginName		( loginName	);
		setLoginPassword	( loginPwd 	);
	}

	
	@Override
	public String toString() {
		return super.toString() + "User\t[userId=" + userId + ", userProfileId=" + userProfileId + ", userName=" + userName + ", loginName="
				+ loginName + ", loginPassword=" + loginPassword + "]\n";
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}