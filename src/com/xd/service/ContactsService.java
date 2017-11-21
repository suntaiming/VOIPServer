package com.xd.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xd.core.CoreConfig;
import com.xd.core.PushCmd;
import com.xd.dao.ContactsDao;
import com.xd.domain.Pageable;
import com.xd.domain.VerifyManager;
import com.xd.model.Contacts;
import com.xd.model.ContactsRelation;
import com.xd.model.ContactsVerify;
import com.xd.model.User;
import com.xd.util.IdGenerator;
import com.xd.util.MD5Util;

@Service
public class ContactsService {
	@Autowired
	ContactsDao contactsDao;
	@Autowired
	UserService userService;
	@Autowired
	ContactsRelationService relationService;
	@Autowired
	ContactsVerifyService verifyService;
	@Autowired
	PushMsgService pushMsgService;
	@Autowired
	UtilService utilService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	/**
	 * 申请添加联系人
	 * @param verify
	 * @param toUser
	 */
	public void applyContacts(ContactsVerify verify){
		if(verify == null){
			throw new NullPointerException("verify对讲为空");
		}
		ContactsRelation relation = getRelationByPhones(verify.getMasterPhone(),verify.getFriendPhone());
		
		User user = userService.getClientIdByPhone(verify.getFriendPhone());
		
		if(relation == null){
			utilService.initContactsRelation(verify);
			String pushCmd = PushCmd.buildApplyContactsCmd(verify.getRelationId(), verify.getMasterPhone(), verify.getRemark());
			pushMsgService.pushMsg(user.getClientId(), pushCmd);
			return ;
		}
		
		switch (relation.getStatus()) {
			case ContactsRelation.STATUS_WAIT:{
				if(relation.getToPhone().equals(verify.getMasterPhone()) && isApplyTimeIn(relation.getApplyTime())){
					passApply(relation, true);
					return;
				}else{
					resetRelation(relation.getRelationId(), verify.getMasterPhone(), verify.getFriendPhone());
				}
				break;
			}
			
			case ContactsRelation.STATUS_DELETE | ContactsRelation.STATUS_REFUSE:{
				resetRelation(relation.getRelationId(), verify.getMasterPhone(), verify.getFriendPhone());
			    break;
			}
			
			case ContactsRelation.STATUS_YES: {
				logger.info("已是好友关系。。。");
				passApply(relation, true);
				return;
			}
			
			default:
				break;
			}
		
		utilService.resetVerify(relation.getRelationId(), verify);
		
		//推送通知
		String pushCmd = PushCmd.buildApplyContactsCmd(verify.getRelationId(), verify.getMasterPhone(), verify.getRemark());
		pushMsgService.pushMsg(user.getClientId(), pushCmd);
	}
	
	
	
	
	/**
	 * 好友通过申请，isPushMsgBoth为true时会推送双方添加好友成功， 为false时只推送发起方
	 * @param relation      好友关系
	 * @param isPushMsgBoth 是否通知双方
	 */
	
	public void passApply(ContactsRelation relation, boolean isPushMsgBoth){
		
		utilService.passApply(relation);
		
		
		String fromPhone = relation.getFromPhone();
		String toPhone = relation.getToPhone();
		
		User user = userService.getClientIdByPhone(fromPhone);
	    Contacts contacts = getContacts(fromPhone, toPhone);
	    String pushCmd = PushCmd.buildAddContactsCmd(contacts.getContactsId(), toPhone, contacts.getName());
	    pushMsgService.pushMsg(user.getClientId(), pushCmd);
	    
	    if(isPushMsgBoth){
	    	User user2 = userService.getClientIdByPhone(toPhone);
	    	Contacts contacts2 = getContacts(toPhone, fromPhone);
	    	String pushCmd2 = PushCmd.buildAddContactsCmd(contacts2.getContactsId(), fromPhone, contacts2.getName());
		    pushMsgService.pushMsg(user2.getClientId(), pushCmd2);
	    }
	}
	
	/**
	 * 好友拒绝申请
	 * @param relationId
	 */
	public void refuseApply(String relationId){
		utilService.refuseApply(relationId);
	}
	
	/**
	 * 申请是否有效时间内
	 * @param applyTime
	 * @return
	 */	
	public boolean isApplyTimeIn(Date applyTime){
		if(new Date().getTime() - applyTime.getTime() >= CoreConfig.CONTACTS_APPLY_TIMEOUT){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 重置好友关系，重置为等待通过的初始状态
	 * @param relationId
	 * @param fromPhone
	 * @param toPhone
	 */
	private void resetRelation(String relationId, String fromPhone, String toPhone){
		ContactsRelation relation = new ContactsRelation();
		relation.setRelationId(relationId);
		relation.setFromPhone(fromPhone);
		relation.setToPhone(toPhone);
		relation.setStatus(ContactsRelation.STATUS_WAIT);
		Date time = new Date();
	    relation.setModifyTime(time);
	    relation.setApplyTime(time);
	 
	    relationService.updateRelation(relation);
	}

	
	
	/**
	 * 根据好友关系添加好友
	 * @param relationId
	 */
	public void saveContacts(String relationId){
		VerifyManager verifyManager = verifyService.getVerifyManager(relationId);
		addContacts(buildContacts(verifyManager.getFromVerify()));
		addContacts(buildContacts(verifyManager.getToVerify()));
	}
	
	private Contacts buildContacts(ContactsVerify verify){
		Date time = new Date();
		Contacts contacts = new Contacts();
		contacts.setContactsId(IdGenerator.nextUuid());
		contacts.setMasterPhone(verify.getMasterPhone());
		contacts.setFriendPhone(verify.getFriendPhone());
		contacts.setName(verify.getName());
		contacts.setRelationId(verify.getRelationId());
		contacts.setModifyTime(time);
		contacts.setRecordTime(time);
		
		return contacts;
	}
	
	private ContactsRelation getRelationByPhones(String phone1, String phone2){
		return relationService.getByPhones(phone1, phone2);
	}
	
	
	 
	
	
	
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
	 * 修改联系人
	 * @param contacts
	 * @return
	 */
	public String update(Contacts contacts){
		
		if(contacts == null){
			throw new NullPointerException("contacts对象为null");
		}
		String contactsSequence = IdGenerator.nextUuid();

        utilService.updateContacts(contacts, contactsSequence);
		return contactsSequence;
	}
	
	/**
	 * 分页查询联系人
	 * @param masterPhone
	 * @param start
	 * @param size
	 * @return
	 */
	
    public Pageable<Contacts> pageContacts(String masterPhone, int start, int pageSize){
    	return contactsDao.pageByMaster(masterPhone, start, pageSize);
    }

    
    /**
     * 删除好友，返回联系人更新序号
     * @param contactsId
     * @return
     */

    public String deleteContacts(String contactsId){
    	Contacts contacts = getByContactsId(contactsId);
    	String contactsSequence = IdGenerator.nextUuid();
    	
    	utilService.deleteByRelationId(contacts.getRelationId());
    	
    	userService.updateContactsSequence(contacts.getMasterPhone(), contactsSequence);
    	userService.updateContactsSequence(contacts.getFriendPhone(), contactsSequence);
    	
    	User user = userService.getClientIdByPhone(contacts.getFriendPhone());
    	//推送穿透消息（好友删除）
    	pushMsgService.pushMsg(user.getClientId(), PushCmd.buildDelContactsCmd(contacts.getMasterPhone()));
    	
    	return contactsSequence;
    }
    

 
    public Contacts getContacts(String masterPhone, String friendPhone){
    	return contactsDao.getContacts(masterPhone, friendPhone);
    }

    public Contacts getByContactsId(String contactsId){
    	return contactsDao.getByContactsId(contactsId);
    }
    
}
