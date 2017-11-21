package com.xd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xd.dao.ContactsRelationDao;
import com.xd.model.ContactsRelation;
import com.xd.model.ContactsVerify;
import com.xd.util.IdGenerator;
/**
 * 好友关系service
 * @author lenovo
 *
 */
@Service
public class ContactsRelationService {
    @Autowired
	ContactsRelationDao relationDao;
	
    /**
     * 根据两个用户的手机号获取相对应的好友关系
     * @param phone1
     * @param phone2
     * @return
     */
  
	public ContactsRelation getByPhones(String phone1, String phone2){
		
		return relationDao.getByPhones(phone1, phone2);
	}
	
	/**
	 * 根据关系ID获取好友关系
	 * @param relationId
	 * @return
	 */

	public ContactsRelation getByRelationId(String relationId){
		
		return relationDao.getByRelationId(relationId);
	}
	
	/**
	 * 更新好友关系
	 * @param relation
	 */
	public void updateRelation(ContactsRelation relation){
	    if(relation == null){
	    	return;
	    }
	    if(relation.getModifyTime() == null){
	    	relation.setModifyTime(new Date());
	    }
	    
	    relationDao.updateRelation(relation);
	}
	
	/**
	 * 更新好友关系状态
	 * @param relationId
	 * @param status
	 */
	public void updateStatus(String relationId, int status){
		relationDao.updateStatus(relationId, status);
	}
  	
	/**
	 * 初始化新的好友关系
	 * @param applyVerify 申请好友验证记录
	 * @return
	 */
    public String initRelation(ContactsVerify applyVerify){
    	String relationId = IdGenerator.nextUuid();
		ContactsRelation relation = new ContactsRelation();
		relation.setRelationId(relationId);
		relation.setFromPhone(applyVerify.getMasterPhone());
		relation.setToPhone(applyVerify.getFriendPhone());
		relation.setStatus(ContactsRelation.STATUS_WAIT);
		relation.setApplyTime(applyVerify.getApplyTime());
		relation.setModifyTime(applyVerify.getApplyTime());
		relation.setRecordTime(applyVerify.getApplyTime());
		
		relationDao.addRelation(relation);
		
		return relationId;
	}
    
    /**
     * 根据手机号查询好友关系列表
     */

    public List<ContactsRelation> findList(String phone1, List<String> phone2s){
    	if(StringUtils.isBlank(phone1) 
    			|| phone2s == null || phone2s.isEmpty()){
    		return new ArrayList<>();
    	}
    	
    	return relationDao.findList(phone1, phone2s);
    	
    }
}
