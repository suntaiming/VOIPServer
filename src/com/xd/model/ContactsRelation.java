package com.xd.model;

import java.util.Date;

public class ContactsRelation {
	/**
	 * 联系人关系状态：待通过
	 */
	public static final int STATUS_WAIT = 0;
	/**
	 * 联系人关系状态：通过
	 */
	public static final int STATUS_YES = 1;
	/**
	 * 联系人关系状态：拒绝
	 */
	public static final int STATUS_REFUSE = 2;
	/**
	 * 联系人关系状态：删除
	 */
	public static final int STATUS_DELETE = 3;
	
	/**
	 * 主键
	 */
	private long id;
	/**
	 * 联系人关系id
	 */
	private String relationId;
	/**
	 * 申请人手机号
	 */
	private String fromPhone;
	/**
	 *被申请人手机号
	 */
	private String toPhone;
	/**
	 * 关系状态：0待通过  1通过  2拒绝  3好友删除 
	 */
	private int status = STATUS_WAIT;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 记录时间
	 */
	private Date recordTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getFromPhone() {
		return fromPhone;
	}
	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}
	public String getToPhone() {
		return toPhone;
	}
	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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
