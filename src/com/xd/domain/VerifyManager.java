package com.xd.domain;

import com.xd.model.ContactsVerify;

public class VerifyManager {
	
	private ContactsVerify fromVerify;
	private ContactsVerify toVerify;
	
	public VerifyManager(){};
	
	public VerifyManager(ContactsVerify fromVerify, ContactsVerify toVerify){
		this.fromVerify = fromVerify;
		this.toVerify = toVerify;
	}
	
	public ContactsVerify getFromVerify() {
		return fromVerify;
	}
	public void setFromVerify(ContactsVerify fromVerify) {
		this.fromVerify = fromVerify;
	}
	public ContactsVerify getToVerify() {
		return toVerify;
	}
	public void setToVerify(ContactsVerify toVerify) {
		this.toVerify = toVerify;
	}
	
	

}
