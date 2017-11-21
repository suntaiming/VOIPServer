package com.xd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xd.dao.ContactsDao;
import com.xd.model.Contacts;
import com.xd.model.ContactsRelation;
import com.xd.model.ContactsVerify;
import com.xd.util.IdGenerator;

@Service
@Transactional
public class UtilService {

	@Autowired
	ContactsDao contactsDao;
	@Autowired
	UserService userService;
	@Autowired
	ContactsRelationService relationService;
	@Autowired
	ContactsVerifyService verifyService;
	@Autowired
	ContactsService contactsService;
	/**
	 * 添加联系人
	 * @param contacts
	 */
	
	public String addContacts(Contacts contacts){
		String contactsSequence = IdGenerator.nextUuid();
	    
		if(contacts.getRecordTime() == null){
			Date now = new Date();
			contacts.setModifyTime(now);
			contacts.setRecordTime(now);
		} 
		contactsDao.addContacts(contacts);
		userService.updateContactsSequence(contacts.getMasterPhone(), contactsSequence);
		return contactsSequence;
	}
	
	/**
	 * 接受好友时对数据库的操作
	 * @param relation
	 */
	public void passApply(ContactsRelation relation){
		//添加联系人
		contactsService.saveContacts(relation.getRelationId());
		//关系状态置为通过
		relationService.updateStatus(relation.getRelationId(), ContactsRelation.STATUS_YES);
		
		//验证记录置为通过
		verifyService.updateStatus(relation.getRelationId(), ContactsVerify.STATUS_YES);
		
		
	}
	
	/**
	 * 初始化好友关系
	 */
	public void initContactsRelation(ContactsVerify verify){
		
		verifyService.initVerifys(relationService.initRelation(verify), verify);
	}
	/**
	 * 重置验证信息
	 * @param relationId
	 * @param verify
	 */
	public void resetVerify(String relationId, ContactsVerify verify){
		verifyService.deleteByRelationId(relationId);
		verifyService.initVerifys(relationId, verify);
	}
	
	/**
	 * 好友拒绝申请
	 * @param relationId
	 */
	public void refuseApply(String relationId){
		//关系状态置为拒绝
		relationService.updateStatus(relationId, ContactsRelation.STATUS_REFUSE);
		//验证记录置为拒绝
		verifyService.updateStatus(relationId, ContactsVerify.STATUS_REFUSE);
	}

	/**
	 * 修改该联系人信息
	 * @param contacts
	 * @param contactsSequence
	 */
	public void updateContacts(Contacts contacts, String contactsSequence){
		contactsDao.update(contacts);
		userService.updateContactsSequence(contacts.getMasterPhone(), contactsSequence);
	}
	
	/**
     * 删联系人：删除好友列表  修改关系状态  修改新好友验证状态
     * @param relationId
     */
    public void deleteByRelationId(String relationId){
    	relationService.updateStatus(relationId, ContactsRelation.STATUS_DELETE);
    	verifyService.updateStatus(relationId, ContactsVerify.STATUS_DELETE);
    	contactsDao.deleteByRelationId(relationId);
    	
    }
	
}
