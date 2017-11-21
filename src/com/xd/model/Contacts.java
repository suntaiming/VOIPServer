package com.xd.model;

import java.util.Date;

public class Contacts {
	//主键
	private long id;
	//联系id
	private String contactsId;
	//联系人关系id
	private String relationId;
	//主用户手机号
	private String masterPhone;
	//联系人手机号
	private String friendPhone;
	//联系人名字
	private String name;
	//联系人公司名
	private String company;
	//联系人邮箱
	private String email;
	//备注
	private String remark;
	//联系人头像
	private String headImage;
	//修改时间
	private Date modifyTime;
	//记录时间
	private Date recordTime;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContactsId() {
		return contactsId;
	}
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}
	
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getMasterPhone() {
		return masterPhone;
	}
	public void setMasterPhone(String masterPhone) {
		this.masterPhone = masterPhone;
	}
	public String getFriendPhone() {
		return friendPhone;
	}
	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
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
