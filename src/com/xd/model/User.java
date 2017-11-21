package com.xd.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户模型
 * @author lenovo
 *
 */
public class User implements Serializable{

	//用户转态：正常
	public static final int STATUS_NOMAL = 1;
	//用户转态：停用
	public static final int STATUS_BLOCK = 2;
	/**
	 * 主键
	 */
	private int id;
	//用户ID
	private String userId;
	//用户名（ 手机号）
	private String username;
	//密码
	private String password;
	//手机号
	private String phoneNumber;
	//用户昵称
	private String name;
	//性别  1男 2女
	private int sex;
	//年龄
	private int age;
	//公司名称
	private String company;
	//邮箱1
	private String email;
	//公钥
	private String publicKey;
	//公钥序号
	private String keySequence;
	//联系人更新序号
	private String contactsSequence;
	//用户状态 1 正常  2 停用    
	private int status = STATUS_NOMAL;
	//头像
	private String headImage;
	//个推clientId
    private String clientId;
	//修改时间
	private Date modifyTime;
	//注册时间
	private Date recordTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getKeySequence() {
		return keySequence;
	}
	public void setKeySequence(String keySequence) {
		this.keySequence = keySequence;
	}
	public String getContactsSequence() {
		return contactsSequence;
	}
	public void setContactsSequence(String contactsSequence) {
		this.contactsSequence = contactsSequence;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	

	
}
