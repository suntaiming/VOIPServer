package com.xd.model;

import java.util.Date;

public class ContactsVerify {
	
	/**
	 * 类型：申请
	 */
	public static final int TYPE_APPLY = 1;
	/**
	 * 类型：被申请
	 */
	public static final int TYPE_APPROVEL = 2;
	
	/**
	 * 转态：待通过
	 */
	public static final int STATUS_WAIT = 0;
	/**
	 * 状态：通过
	 */
	public static final int STATUS_YES = 1;
	/**
	 * 状态：拒绝
	 */
	public static final int STATUS_REFUSE = 2;
	/**
	 * 状态：过期（需要计算得出，数据库中无此状态）
	 */
	public static final int STATUS_TIMEOUT = 3;
	/**
	 * 状态：好友已删除
	 */
	public static final int STATUS_DELETE = 4;
	
	
	/**
	 * 主键
	 */
	private long id;
	/**
	 * 联系人验证信息id
	 */
	private String verifyId;
	/**
	 * 联系人关系id
	 */
	private String relationId;
	/**
	 * 主用户手机号
	 */
	private String masterPhone;

	/**
	 * 好友手机号
	 */
	private String friendPhone;
	/**
	 * 联系人名称
	 */
	private String name;
	/**
	 * 申请验证信息
	 */
	private String remark;
	/**
	 * 类型： 1 申请    2被申请  (默认为：申请)
	 */
	private int type = TYPE_APPLY;
	/**
	 * 状态:0待通过  1 通过  2 拒绝  3超时（需要计算得出，数据库中无此状态） 默认为待通过
	 */
	private int status = STATUS_WAIT;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	
	
}
