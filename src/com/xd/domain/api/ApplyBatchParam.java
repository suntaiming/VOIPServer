package com.xd.domain.api;

import java.util.List;

import com.xd.model.ContactsVerify;

public class ApplyBatchParam {

	private String masterPhone;
	private String remark;
	private List<ContactsVerify> applyContacts;
	public String getMasterPhone() {
		return masterPhone;
	}
	public void setMasterPhone(String masterPhone) {
		this.masterPhone = masterPhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ContactsVerify> getApplyContacts() {
		return applyContacts;
	}
	public void setApplyContacts(List<ContactsVerify> applyContacts) {
		this.applyContacts = applyContacts;
	}
	
}
