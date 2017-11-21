package com.xd.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xd.dao.ContactsVerifyDao;
import com.xd.domain.Pageable;
import com.xd.domain.VerifyManager;
import com.xd.model.ContactsVerify;
import com.xd.util.IdGenerator;

@Service
public class ContactsVerifyService {

	@Autowired
	ContactsVerifyDao verifyDao;
	@Autowired
	ContactsService contactsService;
	/**
	 * 根据好友关系ID获取相关验证记录
	 * @param relationId
	 * @return
	 */
	public VerifyManager getVerifyManager(String relationId){
		List<ContactsVerify> verifys = verifyDao.findByRelationId(relationId);
		if(verifys == null || verifys.isEmpty()){
			return null;
		}
		
		VerifyManager verifyManager = null;
		if(verifys.get(0).getType() == ContactsVerify.TYPE_APPLY){
			verifyManager = new VerifyManager(verifys.get(0), verifys.get(1));
		}else{
			verifyManager = new VerifyManager(verifys.get(1), verifys.get(0));
		}
		
		return verifyManager;
	}
	
	/**
	 * 初始化新好友校验记录
	 * @param relationId
	 * @param applyVerify
	 */
	
	public void initVerifys(String relationId, ContactsVerify applyVerify){
		applyVerify.setVerifyId(IdGenerator.nextUuid());
		applyVerify.setRelationId(relationId);
		if(applyVerify.getModifyTime() == null){
			Date time = new Date();
			applyVerify.setApplyTime(time);
			applyVerify.setModifyTime(time);
		}
		addVerify(applyVerify);
		
		ContactsVerify approveVerify = new ContactsVerify();
		approveVerify.setVerifyId(IdGenerator.nextUuid());
		approveVerify.setRelationId(relationId);
		approveVerify.setMasterPhone(applyVerify.getFriendPhone());
		approveVerify.setFriendPhone(applyVerify.getMasterPhone());
		approveVerify.setName(applyVerify.getMasterPhone());
		approveVerify.setType(ContactsVerify.TYPE_APPROVEL);
		approveVerify.setStatus(ContactsVerify.STATUS_WAIT);
		approveVerify.setRemark(applyVerify.getRemark());
		approveVerify.setModifyTime(applyVerify.getModifyTime());
		approveVerify.setApplyTime(applyVerify.getApplyTime());
		addVerify(approveVerify);
	}
	
	/**
	 * 分页查询用户新加好友验证记录
	 * @param masterPhone
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public Pageable<ContactsVerify> pageVerifyByMaster(String masterPhone, int start, int pageSize){
		Pageable<ContactsVerify> pageable = verifyDao.pageVerifyByMaster(masterPhone, start, pageSize);
		List<ContactsVerify> verifies = pageable.getList();
		if(verifies != null && !verifies.isEmpty()){
			
			for(ContactsVerify verify : verifies){
				if(!contactsService.isApplyTimeIn(verify.getApplyTime())){
					verify.setStatus(ContactsVerify.STATUS_TIMEOUT);
				}
				
			}
		}
		
		return pageable;
	}
	
    
	
	private void addVerify(ContactsVerify verify){
		verifyDao.addVerify(verify);
	}
	
	/**
	 * 根据关系ID修改验证状态
	 * @param relationId
	 * @param status
	 */
	public void updateStatus(String relationId, int status){
		verifyDao.updateStatus(relationId, status);
	}
	
	/**
	 * 根据关系ID删除相关验证记录
	 * @param relationId
	 */
	public void deleteByRelationId(String relationId){
		verifyDao.deleteByRelationId(relationId);
	}
	/**
	 * 根据验证记录ID删除验证记录
	 * @param verifyId
	 */
	public void deleteByVerifyId(String verifyId){
		verifyDao.deleteByVerifyId(verifyId);
	}
}
