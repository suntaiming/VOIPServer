package com.xd.domain.api;

import java.util.List;

public class CheckPhonesParam {
	private String masterPhone;
	private List<String> checkPhones;
	public String getMasterPhone() {
		return masterPhone;
	}
	public void setMasterPhone(String masterPhone) {
		this.masterPhone = masterPhone;
	}
	public List<String> getCheckPhones() {
		return checkPhones;
	}
	public void setCheckPhones(List<String> checkPhones) {
		this.checkPhones = checkPhones;
	}
	

}
