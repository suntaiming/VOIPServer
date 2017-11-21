package com.xd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xd.model.ContactsRelation;
/**
 * 好友关系Dao
 * @author lenovo
 *
 */
@Repository
public class ContactsRelationDao extends BaseDao{

	/**
	 * 添加好友关系
	 * @param relation
	 */
	public void addRelation(ContactsRelation relation){
		if(relation == null){
			return ;
		}
		writerSqlSession.insert("ContactsRelationDao.insert", relation);
	}
	
	
	/**
	 * 通过两个手机号查找对应的好友关系
	 * @param phone1
	 * @param phone2
	 * @return
	 */
    public ContactsRelation getByPhones(String phone1, String phone2){
		Map<String, String> params = new HashMap<>();
		params.put("phone1", phone1);
		params.put("phone2", phone2);
		
		return readSqlSession.selectOne("ContactsRelationDao.getByPhones", params);
	}
    
    /**
     * 查询好友关系列表
     * @param fromPhone
     * @param toPhones
     * @return
     */
    public List<ContactsRelation> findList(String phone1, List<String> phone2s){
    	Map<String, Object> params = new HashMap<>();
    	params.put("phone1", phone1);
    	params.put("phone2s", phone2s);
    	return readSqlSession.selectList("ContactsRelationDao.findList", params);
    }
    
    public ContactsRelation getByRelationId(String relationId){
    	return readSqlSession.selectOne("ContactsRelationDao.getByRelationId", relationId);
    }
    /**
     * 更新好友关系对象
     * @param relation
     */
    public void updateRelation(ContactsRelation relation){
		if(relation == null){
			return ;
		}
		writerSqlSession.update("ContactsRelationDao.updateRelation", relation);
	}
    
    /**
     * 更新好友关系状态
     * @param relationId
     * @param Status
     */
    public void updateStatus(String relationId, int status){
		Map<String, Object> params = new HashMap<>();
		params.put("relationId", relationId);
		params.put("status", status);
		params.put("modifyTime", new Date());
		
		writerSqlSession.update("ContactsRelationDao.updateStatus", params);
	}
}
