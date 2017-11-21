package com.xd.domain;

public class PhoneCheckResult {
	/**
	 * 状态：未注册
	 */
	public static final int STATUS_NO_REGISTER = 0;
	/**
	 * 已注册未添加联系人
	 */
	public static final int STATUS_REGISTER = 1;
	/**
	 * 已添加联系人
	 */
	public static final int STATUS_CONTACT = 2;
	/**
	 * 已申请待通过
	 */
	public static final int STATUS_APPLY_WAIT = 3;
	/**
	 * 待处理
	 */
	public static final int STATUS_APPROVE_WAIT = 4;
	/**
	 * 申请超时
	 */
	public static final int STATUS_APPLY_TIMEOUT = 5;
	/**
	 * 待处理超时
	 */
	public static final int STATUS_APPROVE_TIMEOUT = 6;
	
	
	
	private String phoneNumber;
    private String name;
    private int status = STATUS_NO_REGISTER;  
    
    
    
	public PhoneCheckResult() {
		super();
	}
	public PhoneCheckResult(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    
}
