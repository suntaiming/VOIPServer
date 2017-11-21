package com.xd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xd.domain.Pageable;
import com.xd.model.ContactsVerify;

@Repository
public class ContactsVerifyDao extends BaseDao{

	
	/**
	 * 根据好友关系id查找新好友验证记录（最多有两条）
	 * @param relationId
	 * @return
	 */
	public List<ContactsVerify> findByRelationId(String relationId){
	
		return readSqlSession.selectList("ContactsVerifyDao.findByRelationId", relationId);
	}
	
	/**
	 * 新增新好友验证记录
	 * @param verify
	 */
	public void addVerify(ContactsVerify verify){
		
		writerSqlSession.insert("ContactsVerifyDao.insert", verify);
	}
	
	/**
	 * 分页查询用户新加好友验证记录
	 * @param masterPhone
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public Pageable<ContactsVerify> pageVerifyByMaster(String masterPhone, int start, int pageSize){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterPhone", masterPhone);
		params.put("start", start);
		params.put("pageSize", pageSize);
		List<ContactsVerify> verifies = readSqlSession.selectList("ContactsVerifyDao.pageContactsVerifyByMaster", params);
		long count = count(masterPhone);
		
		Pageable<ContactsVerify> pageable = new Pageable<>(count, verifies);
		
		return pageable;		
	}
	
	/**
	 * 查询用户下新加好友验证记录总条数
	 * @param masterPhone 
	 * @return
	 */
	public long count(String masterPhone){
		return readSqlSession.selectOne("ContactsVerifyDao.count", masterPhone);
	}
	
	/**
	 * 
	 * 修改验证状态
	 * @param verifyId
	 * @param status
	 */
	public void updateStatus(String relationId, int status){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relationId", relationId);
		params.put("status", status);
		params.put("modifyTime", new Date());
		
		writerSqlSession.update("ContactsVerifyDao.updateStatus", params);
		
	}
	
	/**
	 * 根据验证记录ID删除新好友验证记录
	 * @param verifyId
	 */
	public void deleteByVerifyId(String verifyId){
		writerSqlSession.update("ContactsVerifyDao.deleteByVerifyId", verifyId);
	}
	/**
	 * 根据好友关系ID删除新好友验证记录
	 * @param verifyId
	 */
	public void deleteByRelationId(String relationId){
		writerSqlSession.update("ContactsVerifyDao.deleteByRelationId", relationId);
	}
}
