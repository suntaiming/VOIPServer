package com.xd.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xd.dao.UserDao;
import com.xd.domain.PhoneCheckResult;
import com.xd.model.ContactsRelation;
import com.xd.model.User;
import com.xd.util.IdGenerator;
import com.xd.util.MD5Util;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	ContactsRelationService relationService;
	@Autowired
	ContactsService contactsService;
	

	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public User getUser(String username){
		return userDao.getByUsername(username);
	}
	
	/**
	 * 获取用户（只包含username和password）
	 * @param username
	 * @return
	 */
	public User loginUser(String username){
		return userDao.loginUser(username);
	}
	
	/**
	 * 根据手机号获取用户
	 * @param phoneNumber
	 * @return
	 */
	public User getUserByPhone(String phoneNumber){
		return userDao.getUserByPhone(phoneNumber);
	}
	/**
	 * 根据手机号获取用户
	 * @param phoneNumber
	 * @return
	 */
	public User getClientIdByPhone(String phoneNumber){
		return userDao.getClientIdByPhone(phoneNumber);
	}
	
	public void updatePassword(String phoneNumber, String password){
		userDao.updatePassword(phoneNumber, password, new Date());
	}
	
	public void updateClient(String phoneNumber, String clientId){
		userDao.updateClient(phoneNumber, clientId);
	}
	

	
	/**
	 * 修改联系人跟新序号
	 * @param phoneNumber
	 * @param contactsSequence
	 */
	public void updateContactsSequence(String phoneNumber, String contactsSequence){
		userDao.updateContactsSequence(phoneNumber, contactsSequence);
	}
	
	/**
	 * 添加用户
	 * @param user
	 */
	public void saveUser(User user){
		if(user == null){
			return ;
		}
		
		if(StringUtils.isBlank(user.getUsername())){
			return ;
		}
		if(StringUtils.isBlank(user.getUserId())){
			user.setUserId(IdGenerator.nextUuid());
		}
		
		if(user.getRecordTime() == null){
			Date time = new Date();
			user.setModifyTime(time);
			user.setRecordTime(time);
		}
		userDao.addUser(user);
	}
	
	/**
	 * 更新用户公钥，返回更新序号
	 * @param phoneNumber
	 * @param publicKey
	 * @return 
	 */
	public String updatePublicKey(String phoneNumber, String publicKey){
		String keySequence = MD5Util.stringMD5(publicKey);
		userDao.updatePublicKey(phoneNumber, publicKey, keySequence);
		return keySequence;
	}
	
	
	/**
	 * 批量检测手机号
	 * @param masterPhone
	 * @param checkPhones
	 * @return
	 */
	public Map<String, PhoneCheckResult> checkPhoneNumbers(String masterPhone, List<String> checkPhones){
		Map<String, PhoneCheckResult> results = new HashMap<>();
		for(String checkPhone : checkPhones){
			results.put(checkPhone, new PhoneCheckResult(checkPhone));
		}
		
		List<User> users = userDao.findByPhones(checkPhones);
		List<String> phoneNumbers = new ArrayList<>();
		for(User user : users){
			PhoneCheckResult phoneCheckResult = results.get(user.getPhoneNumber());
			phoneCheckResult.setName(user.getName());
			phoneCheckResult.setStatus(PhoneCheckResult.STATUS_REGISTER);
			phoneNumbers.add(user.getPhoneNumber());
		}
		
		List<ContactsRelation> relations = relationService.findList(masterPhone, phoneNumbers);
		
		for(ContactsRelation relation : relations){
			
			PhoneCheckResult phoneCheckResult = null;
			if(relation.getFromPhone().equals(masterPhone)){
				phoneCheckResult = results.get(relation.getToPhone());
				if(relation.getStatus() == ContactsRelation.STATUS_WAIT){
					if(contactsService.isApplyTimeIn(relation.getApplyTime())){
						phoneCheckResult.setStatus(PhoneCheckResult.STATUS_APPLY_WAIT);
					}else{
						phoneCheckResult.setStatus(PhoneCheckResult.STATUS_APPLY_TIMEOUT);
					}
					
				}
			}else{
				phoneCheckResult = results.get(relation.getFromPhone());
				if(relation.getStatus() == ContactsRelation.STATUS_WAIT){
					if(relation.getStatus() == ContactsRelation.STATUS_WAIT){
						phoneCheckResult.setStatus(PhoneCheckResult.STATUS_APPROVE_WAIT);
					}else{
						phoneCheckResult.setStatus(PhoneCheckResult.STATUS_APPROVE_TIMEOUT);
					}
					
				}
			}
			
			if(relation.getStatus() == ContactsRelation.STATUS_YES){
				phoneCheckResult.setStatus(PhoneCheckResult.STATUS_CONTACT);
			}
			
		}
		
		return results;
		
	}
	
	
	public void deleteUser(String username){
		userDao.deleteUser(username);
	}
	
	
	
	

}
