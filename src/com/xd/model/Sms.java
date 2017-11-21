package com.xd.model;

import java.util.Date;
/**
 * 验证码模型
 * @author lenovo
 *
 */
public class Sms {
	//主键
	private long id;
	//手机号
	private String phoneNumber;
	//最新验证码
	private String verifyCode;
	//时间
	private long time;
	//记录时间
	private Date recordTime;

	
	
	public Sms() {
		super();
	}

	public Sms(String phoneNumber, String verifyCode, long time, Date recordTime) {
		super();
		this.phoneNumber = phoneNumber;
		this.verifyCode = verifyCode;
		this.time = time;
		this.recordTime = recordTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	

	
}
