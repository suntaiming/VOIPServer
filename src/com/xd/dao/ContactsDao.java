package com.xd.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.xd.domain.Pageable;
import com.xd.model.Contacts;

@Repository
public class ContactsDao extends BaseDao{
	
	public void addContacts(Contacts contacts){
		if(contacts == null){
			return;
		}
		
		writerSqlSession.insert("ContactsDao.addContacts", contacts);
	}
	
	public List<Contacts> findContacts(String masterPhone){
		if(StringUtils.isBlank(masterPhone)){
			return new ArrayList<>();
		}
		return readSqlSession.selectList("ContactsDao.findContactsByMaster", masterPhone);
		
	}
	
	/**
	 * 分页查询联系人
	 * 
	 * @param masterPhone   主用户手机号
	 * @param start         开始下标
	 * @param size          每页条数
	 * @return
	 */
	public Pageable<Contacts> pageByMaster(String masterPhone, int start, int pageSize){
		if(StringUtils.isBlank(masterPhone)){
			return new Pageable<Contacts>(0, new ArrayList<Contacts>());
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("masterPhone", masterPhone);
		params.put("start", start);
		params.put("pageSize", pageSize);
		
		List<Contacts> contactsList = readSqlSession.selectList("ContactsDao.pageContactsByMaster", params);
	    long count = count(masterPhone);
	    
		return new Pageable<Contacts>(count, contactsList);
	}
	
	
	
	public void update(Contacts contacts){
		if(contacts == null){
			return ;
		}
		
		writerSqlSession.update("ContactsDao.update", contacts);
	}
	
	public long count(String masterPhone){
		 if(StringUtils.isBlank(masterPhone)){
			 return 0;
		 }
		 return readSqlSession.selectOne("ContactsDao.count", masterPhone);
	}
	
	public Contacts getByContactsId(String contactsId){
		return readSqlSession.selectOne("ContactsDao.getByContactsId", contactsId);
	}
	
	public Contacts getContacts(String masterPhone, String friendPhone){
		Map<String, String> params = new HashMap<>();
		params.put("masterPhone", masterPhone);
		params.put("friendPhone", friendPhone);
		return readSqlSession.selectOne("ContactsDao.getContacts", params);
	}
	
	public void deleteByContactsId(String contactsId){
		writerSqlSession.delete("ContactsDao.deleteByContactsId", contactsId);
	}
	
	/**
	 * 根据好友关系ID删除好友列表
	 * @param relationId
	 */
	public void deleteByRelationId(String relationId){
		writerSqlSession.delete("ContactsDao.deleteByRelationId", relationId);
	}

}
