package com.xd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xd.core.CoreConfig;
import com.xd.dao.SmsDao;
import com.xd.model.Sms;
import com.xd.service.sms.request.SmsSendRequest;
import com.xd.service.sms.response.SmsSendResponse;
import com.xd.service.sms.util.ChuangLanSmsUtil;
import com.xd.util.GsonUtil;
import com.xd.util.NumberUtil;

@Service
public class SmsService {
	@Autowired
	SmsDao smsDao;
	
	
	/**
	 * 保存验证码，如果存在改手机号对应的记录 则又该 否则新增
	 * @param sms
	 */
	public void saveSms(Sms sms){
		if(sms == null){
			return;
		}
		
		if(sms.getRecordTime() == null){
			Date time = new Date();
			sms.setTime(time.getTime());
			sms.setRecordTime(time);
		}
		Sms originSms = getSms(sms.getPhoneNumber());
		if(originSms == null){
			smsDao.addSms(sms);
		}else{
			updateSms(sms);
		}
		
		
	}
	
	/**
	 * 发送手机验证码短信
	 * @param phoneNumber 单个手机号
	 * @return
	 */
	public boolean sendSms(String phoneNumber){
		if(phoneNumber == null){
			return false;
		}
		
		int verifyCode = NumberUtil.get6RandNum();
		String msg = String.format(CoreConfig.smsMessage, verifyCode);
		
		SmsSendRequest sendRequest = new SmsSendRequest(CoreConfig.SMS_ACCOUNT, CoreConfig.SMS_PW, msg, phoneNumber, CoreConfig.SMS_REPORT);
	    String result = ChuangLanSmsUtil.sendSmsByPost(CoreConfig.SMS_URL, GsonUtil.toJson(sendRequest));
	    SmsSendResponse smsSendResponse = GsonUtil.fromJson(result, SmsSendResponse.class);
	   
	    if(smsSendResponse.getCode().equals(0)){
	    	return false;
	    }
	    
	    Date time = new Date();
	    Sms sms = new Sms(phoneNumber, String.valueOf(verifyCode), time.getTime(), time);
	    saveSms(sms);
	    
	    //TODO:后续可能会对响应code及msgId做后续的处理，目前暂不需要
	    
	    return true;
	}
	
	/**
	 * 校验手机验证码
	 * @param phoneNumber 手机号
	 * @param verifyCode  验证码
	 * @return
	 */
	public boolean verifyCode(String phoneNumber, String verifyCode){
		Sms sms = getSms(phoneNumber);
		if(sms == null){
			return false;
		}
		long nowTime = new Date().getTime();
		
		if(nowTime - CoreConfig.SMS_LOSE_TIME > sms.getTime()){
			return false;
		}
		
		if(!sms.getVerifyCode().equals(verifyCode)){
			return false;
		}
		return true;
	}
	
	public Sms getSms(String phoneNumber){
		return smsDao.getSms(phoneNumber);
	}
	
	public void updateSms(Sms sms){
		if(sms == null){
			return;
		}
		smsDao.updateSms(sms);
	}

}
