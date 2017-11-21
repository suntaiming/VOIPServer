package com.xd.dao;

import org.springframework.stereotype.Repository;

import com.xd.model.Sms;

@Repository
public class SmsDao extends BaseDao{

	public Sms getSms(String phoneNumber){
		return readSqlSession.selectOne("SmsDao.getByPhoneNumber", phoneNumber);
	}
	
	public void updateSms(Sms sms){
		if(sms == null){
			return;
		}
		writerSqlSession.update("SmsDao.updateSms", sms);
	}
	
	public void addSms(Sms sms){
		if(sms == null){
			return;
		}
		writerSqlSession.insert("SmsDao.addSms", sms);
	}
	
	public void deleteSms(String phoneNumber){
		writerSqlSession.delete("SmsDao.deleteByPhoneNumber", phoneNumber);
	}
}
