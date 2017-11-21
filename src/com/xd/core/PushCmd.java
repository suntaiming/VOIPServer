package com.xd.core;

public class PushCmd {
	
	public final static String APPLY_CONTACTS = "applyContacts";
	public final static String DELETE_CONTACTS = "deleteContacts";
	public final static String ADD_CONTACTS = "addContacts";

	
	
	
	/**
	 * 构建推送指令：好友申请 
	 * @param relationId     关系ID
	 * @param friendPhone    好友手机号
	 * @param remark         验证信息
	 * @return
	 */
	public static String buildApplyContactsCmd(String relationId, String friendPhone, String remark){
		StringBuilder builder = new StringBuilder(APPLY_CONTACTS);
		builder.append(",")
		    .append(relationId)
		    .append(",")
		    .append(friendPhone)
		    .append(",")
		    .append(remark);
		return builder.toString();
	}
	
	/**
	 * 构建推送指令：删除联系人
	 * @param friendPhone
	 * @return
	 */
	public static String buildDelContactsCmd(String friendPhone){
		StringBuilder builder = new StringBuilder(DELETE_CONTACTS);
		builder.append(",")
		    .append(friendPhone);
		
		return builder.toString();
	}
	
	/**
	 * 构建推送指令：添加联系人
	 * @param contactsId
	 * @param friendPhone
	 * @param name
	 * @return
	 */
	public static String buildAddContactsCmd(String contactsId, String friendPhone, String name){
		StringBuilder builder = new StringBuilder(ADD_CONTACTS);
		builder.append(",")
		    .append(contactsId)
		    .append(",")
		    .append(friendPhone)
		    .append(",")
		    .append(name);
		
		return builder.toString();
	}
}
