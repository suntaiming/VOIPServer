package com.xd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xd.model.User;



@Repository
public class UserDao extends BaseDao{

	
	public void addUser(User user){
	
		writerSqlSession.insert("UserDao.addUser", user);
	}
	
	public User getByUsername(String username){
		return readSqlSession.selectOne("UserDao.getByUsername", username);
		
	}
	
	public User getClientIdByPhone(String username){
		return readSqlSession.selectOne("UserDao.getClientIdByPhone", username);
		
	}
	
	public User getUserByPhone(String phoneNumber){
		return readSqlSession.selectOne("UserDao.getUserByPhone", phoneNumber);
		
	}
	public User loginUser(String username){
		return readSqlSession.selectOne("UserDao.loginUser", username);
	}
	
	public void updatePassword(String phoneNumber, String password, Date modifyTime){
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNumber", phoneNumber);
		params.put("password", password);
		params.put("modifyTime", modifyTime);
		writerSqlSession.update("UserDao.updatePassword", params);
	}
	public void updateClient(String phoneNumber, String clientId){
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNumber", phoneNumber);
		params.put("clientId", clientId);
		params.put("modifyTime", new Date());
		writerSqlSession.update("UserDao.updateClientId", params);
	}
	
	public void updatePublicKey(String phoneNumber, String publicKey, String keySequence){
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNumber", phoneNumber);
		params.put("publicKey", publicKey);
		params.put("keySequence", keySequence);
		params.put("modifyTime", new Date());
		
		writerSqlSession.update("UserDao.updatePublicKey", params);
	}
	
	/**
	 * 根据手机号查询用户
	 * @param phoneNumbers
	 * @return
	 */
	public List<User> findByPhones(List<String> phoneNumbers){
		return readSqlSession.selectList("UserDao.findByPhones", phoneNumbers);
	}
	
	
	
	
	public void updateContactsSequence(String phoneNumber, String contactsSequence){
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNumber", phoneNumber);
		params.put("contactsSequence", contactsSequence);
		writerSqlSession.update("UserDao.updateContactsSequence", params);
	}
	
	public void deleteUser(String username){
		writerSqlSession.insert("UserDao.deleteUser", username);
	}
	
	
}
