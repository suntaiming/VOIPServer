package com.xd.web.controller;




import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xd.core.CoreConfig;
import com.xd.core.token.TokenManager;
import com.xd.domain.Pageable;
import com.xd.domain.PhoneCheckResult;
import com.xd.domain.api.ApplyBatchParam;
import com.xd.domain.api.CheckPhonesParam;
import com.xd.domain.api.ResponseMsg;
import com.xd.model.Contacts;
import com.xd.model.ContactsRelation;
import com.xd.model.ContactsVerify;
import com.xd.model.User;
import com.xd.service.ContactsRelationService;
import com.xd.service.ContactsService;
import com.xd.service.ContactsVerifyService;
import com.xd.service.SmsService;
import com.xd.service.UserService;
import com.xd.util.GsonUtil;
import com.xd.util.MD5Util;

import static com.xd.core.Code.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/api/")
public class ApiController {

	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String ERRNO_SUCCESS = "1";
	public final static String ERRNO_FAIL = "-1";
	
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
	
	@Autowired
	UserService userService;
	@Autowired
	SmsService smsService;  
	@Autowired
	ContactsService contactsService;
	@Autowired
	ContactsVerifyService verifyService;
	@Autowired
	ContactsRelationService relationService;
	
	/**
	 * 不带验签版本
	 * @param cmd
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/{cmd}", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String terminalApi(@PathVariable(value = "cmd") String cmd, String reqMsg,HttpServletRequest request, HttpServletResponse response){
		
		if(StringUtils.isBlank(reqMsg)){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		String responseMsg = handleApi(cmd, reqMsg, request, response);
		
		return responseMsg;
	}
	
	/**
	 * 带验签api接口
	 * @param cmd
	 * @param reqMsg
	 * @param sign
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v2/{cmd}", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String terminalApi2(@PathVariable(value = "cmd") String cmd, String reqMsg, String sign,HttpServletRequest request, HttpServletResponse response){
		String responseMsg = "";
		if(StringUtils.isBlank(reqMsg)){
			return signature(GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试")));
		}
		
		if(!verifySign(reqMsg, sign)){
			return signature(GsonUtil.toJson(new ResponseMsg(CODE_VERIFYSIGN_FAIL, "验签失败")));
		}
		responseMsg = handleApi(cmd, reqMsg, request, response);
		
		return signature(responseMsg);
	}
	
	public String handleApi(String cmd, String reqMsg, HttpServletRequest request, HttpServletResponse response){
		String responseMsg = "";
		switch (cmd) {
		case "login":{
			logger.info("登录请求：{}", reqMsg);
			responseMsg = login(reqMsg, response);
			logger.info("登录响应：{}", responseMsg);
			break;
		}
		case "register":{
			logger.info("用户注册请求：{}", reqMsg);
		    responseMsg = register(reqMsg);
			logger.info("用户注册响应：{}", responseMsg);
			break;
		}
		case "resetPassword":{
			logger.info("重置密码请求：{}", reqMsg);
			responseMsg = resetPassword(reqMsg);
			logger.info("重置密码响应：{}", responseMsg);
			break;
		}
		case "isExistUser":{
			logger.info("用户是否存在请求：{}", reqMsg);
			responseMsg = isExistUser(reqMsg);
			logger.info("用户是否存在响应：{}", responseMsg);
			break;
		}
		case "sendSms":{
			logger.info("请求发送验证码请求：{}", reqMsg);
			responseMsg = sendSms(reqMsg);
			logger.info("请求发送验证码响应：{}", responseMsg);
			break;
		}
		case "verifyCode":{
			logger.info("检验手机验证码请求：{}", reqMsg);
			responseMsg = verifyCode(reqMsg);
			logger.info("检验手机验证码响应：{}", responseMsg);
			break;
		}
		case "pageContacts":{
			logger.info("分页查询联系人请求：{}", reqMsg);
			responseMsg = pageContacts(reqMsg);
			logger.info("分页查询联系人响应：{}", responseMsg);
			break;
		}
		
		case "updateContacts":{
			logger.info("修改联系人请求：{}", reqMsg);
			responseMsg = updateContacts(reqMsg);
			logger.info("修改联系人响应：{}", responseMsg);
			break;
		}
		case "deleteContacts":{
			logger.info("删除联系人请求：{}", reqMsg);
			responseMsg = deleteContacts(reqMsg);
			logger.info("删除联系人响应：{}", responseMsg);
			break;
		}
		case "searchUser":{
			logger.info("搜索用户请求：{}", reqMsg);
			responseMsg = searchUser(reqMsg);
			logger.info("搜索用户响应：{}", responseMsg);
			break;
		}
		case "getContactsSequence":{
			logger.info("获取联系人更新序号请求：{}", reqMsg);
			responseMsg = getContactsSequence(reqMsg);
			logger.info("获取联系人更新序号响应：{}", responseMsg);
			break;
		}
		case "applyContacts":{
			logger.info("申请添加好友请求：{}", reqMsg);
			responseMsg = applyContacts(reqMsg);
			logger.info("申请添加好友响应：{}", responseMsg);
			break;
		}
		
		case "pageContactsVerifys":{
			logger.info("分页查询新好友验证记录请求：{}", reqMsg);
			responseMsg = pageContactsVerifys(reqMsg);
			logger.info("分页查询新好友验证记录响应：{}", responseMsg);
			break;
		}	
		case "handleApplyVerify":{
			logger.info("处理好友验证申请请求：{}", reqMsg);
			responseMsg = handleApplyVerify(reqMsg);
			logger.info("处理好友验证申请响应：{}", responseMsg);
			break;
		}	
		case "deleteContactsVerify":{
			logger.info("删除新好友验证记录请求：{}", reqMsg);
			responseMsg = deleteContactsVerify(reqMsg);
			logger.info("删除新好友验证记录响应：{}", responseMsg);
			break;
		}	
		case "getUserInfo":{
			logger.info("获取用户信息请求：{}", reqMsg);
			responseMsg = getUserInfo(reqMsg);
			logger.info("获取用户信息响应：{}", responseMsg);
			break;
		}	
		case "submitPublicKey":{
			logger.info("上传用户公钥请求：{}", reqMsg);
			responseMsg = submitPublicKey(reqMsg);
			logger.info("上传用户公钥响应：{}", responseMsg);
			break;
		}	
		case "getFriendPublicKey":{
			logger.info("获取联系人公钥请求：{}", reqMsg);
			responseMsg = getFriendPublicKey(reqMsg);
			logger.info("获取联系人公钥响应：{}", responseMsg);
			break;
		}	
		case "checkPhoneNumbers":{
			logger.info("检测手机号请求：{}", reqMsg);
			responseMsg = checkPhoneNumbers(reqMsg);
			logger.info("检测手机号响应：{}", responseMsg);
			break;
		}	
		case "applyContactsBatch":{
			logger.info("批量申请好友请求：{}", reqMsg);
			responseMsg = applyContactsBatch(reqMsg);
			logger.info("批量申请好友响应：{}", responseMsg);
			break;
		}	
		case "submitClientId":{
			logger.info("上传用户个推ClientId请求：{}", reqMsg);
			responseMsg = submitClient(reqMsg);
			logger.info("上传用户个推ClientId响应：{}", responseMsg);
			break;
		}	

		default:
			logger.info("请求：{}", reqMsg);
			responseMsg = GsonUtil.toJson(new ResponseMsg(CODE_UNKNOW_CMD, "指令不存在"));
			logger.info("响应：{}", responseMsg);
			break;
		}
		
		return responseMsg;
	}
	
	/**
	 * 校验签名
	 * @param reqMsg
	 * @param sign
	 * @return
	 */
	public boolean verifySign(String reqMsg, String sign){
		if(StringUtils.isBlank(sign) || StringUtils.isBlank(reqMsg)){
			return false;
		}
		String md5 = MD5Util.stringMD5(reqMsg + CoreConfig.SERVER_PASSKEY);
		
		if(!md5.equals(sign)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 签名响应数据
	 * @param responseMsg
	 * @return
	 */
	public String signature(String responseMsg){
		String sign = MD5Util.stringMD5(responseMsg + CoreConfig.SERVER_PASSKEY);
		return responseMsg + sign;
	}

	/**
	 * 登录接口
	 * @param reqMsg
	 * @return
	 */
	public String login(String reqMsg, HttpServletResponse response){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String username = String.valueOf(params.get("username")).replace(" ", "");
		String password = String.valueOf(params.get("password"));
		
		User user = userService.loginUser(username);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
		
		if(!user.getPassword().equals(password)){
			return GsonUtil.toJson(new ResponseMsg(CODE_PW_FAIL, "密码不正确"));
		}
		
		//生成并存放token
		TokenManager tokenManager = TokenManager.getInstance();
		String token = tokenManager.addToken(user.getUsername());
		response.addHeader("token", token);
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));		
	}
	
	/**
	 * 注册接口
	 * @param reqMsg
	 * @return
	 */
	public String register(String reqMsg){
		User user = GsonUtil.fromJson(reqMsg, User.class);
		if(user == null || StringUtils.isBlank(user.getUsername()) 
				|| StringUtils.isBlank(user.getPassword())){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		if(!isMobile(user.getUsername())){
			return GsonUtil.toJson(new ResponseMsg(CODE_PHONE_FAIL, "手机号格式不正确"));
		}
		
		User originUser = userService.getUser(user.getUsername());
		if(originUser != null){
			return GsonUtil.toJson(new ResponseMsg(CODE_REPEAT_REGISTER, "该手机号已注册"));
		}
		
		user.setStatus(User.STATUS_NOMAL);
		user.setPhoneNumber(user.getUsername());
		
		userService.saveUser(user);
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	/**
	 * 重置密码
	 * @param reqMsg
	 * @return
	 */
	public String resetPassword(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = null;
		String password = null;
		try {
			phoneNumber = (String)params.get("phoneNumber");
			password = (String)params.get("password");
		} catch (Exception e) {
			// TODO: handle exception
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		User user = userService.getUserByPhone(phoneNumber);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "该用户不存在"));
		}
		
		userService.updatePassword(phoneNumber, password);
		
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	
	/**
	 * 用户是否存在接口
	 * @param reqMsg
	 * @return
	 */
	public String isExistUser(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = String.valueOf(params.get("phoneNumber")) ;
		
		User user = userService.getUserByPhone(phoneNumber);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
			
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	
	/**
	 * 请求发送送验证码
	 * @param reqMsg
	 * @return
	 */
	public String sendSms(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = String.valueOf(params.get("phoneNumber")).replace(" ", "");
		if(!isMobile(phoneNumber)){
			return GsonUtil.toJson(new ResponseMsg(CODE_PHONE_FAIL, "手机号格式不正确"));
		}
		
		boolean isSuccess = smsService.sendSms(phoneNumber);
		if(isSuccess){
			logger.info("验证码已发送到手机号为：{}的用户", phoneNumber);
		}else{
			logger.info("验证码发送失败，手机号为：{}", phoneNumber);
		}
	    
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	/**
	 * 校验手机验证码时候正确
	 * @param reqMsg
	 * @return
	 */
	public String verifyCode(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = String.valueOf(params.get("phoneNumber")).replace(" ", "");
		String verifyCode = String.valueOf(params.get("verifyCode"));
		if(StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(verifyCode)){
			return GsonUtil.toJson(new ResponseMsg(CODE_VERIFY_CODE_FAIL, "手机验证码不正确"));
		}
		
		if(!smsService.verifyCode(phoneNumber, verifyCode)){
			return GsonUtil.toJson(new ResponseMsg(CODE_VERIFY_CODE_FAIL, "手机验证码不正确"));
		}
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
		
	}
	
	/**
	 * 分页查询联系人接口
	 * @param reqMsg
	 * @return
	 */
	public String pageContacts(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		
		String masterPhone = null;
		int pageNumber = 0;
		int pageSize = 0;
        try {
        	masterPhone = (String)params.get("masterPhone");
        	pageNumber = new Double((double) params.get("pageNumber")).intValue();
        	pageSize = new Double((double) params.get("pageSize")).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));		
		}
	   
	    int start = pageSize * (pageNumber - 1);
	    Pageable<Contacts> pageable = contactsService.pageContacts(masterPhone, start, pageSize);
	    
	    ResponseMsg responseMsg = new ResponseMsg(CODE_SUCCESS,"", pageable.getList());
	    responseMsg.setPageNumber(pageNumber);
	    responseMsg.setPageSize(pageSize);
	    responseMsg.setCount(pageable.getCount());
	     
	    return GsonUtil.toJson(responseMsg);
	}
	
	/**
	 * 添加联系人接口
	 * @param reqMsg
	 * @return
	 */
	public String addContacts(String reqMsg){
		Contacts contacts = GsonUtil.fromJson(reqMsg, Contacts.class);
		if(contacts == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		if(StringUtils.isBlank(contacts.getMasterPhone()) 
				&& StringUtils.isBlank(contacts.getFriendPhone())){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		User user = userService.getUserByPhone(contacts.getFriendPhone());
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
		
		Contacts originContacts = contactsService.getContacts(contacts.getMasterPhone(), contacts.getFriendPhone());
		if(originContacts != null){
			return GsonUtil.toJson(new ResponseMsg(CODE_REPEAT_CONTACT, "重复添加联系人"));
		}
		
		contactsService.addContacts(contacts);
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	
	/**
	 * 修改联系人接口
	 * @param reqMsg
	 * @return
	 */
	public String updateContacts(String reqMsg){
		Contacts contacts = GsonUtil.fromJson(reqMsg, Contacts.class);
		if(contacts == null
				|| StringUtils.isBlank(contacts.getContactsId())){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		Contacts originContacts = contactsService.getByContactsId(contacts.getContactsId());
		if(originContacts == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_CONTACT, "联系人不存在"));
		}
		contacts.setModifyTime(new Date());
		String contactsSequence = contactsService.update(contacts);
		Map<String, String> data = new HashMap<>();
		data.put("contactsSequence", contactsSequence);
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
	}
	
	/**
	 * 删除联系人接口
	 * @param reqMsg
	 * @return
	 */
	public String deleteContacts(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String contactsId = (String)params.get("contactsId");
		if(contactsId == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		String contactsSequence = contactsService.deleteContacts(contactsId);
		Map<String, String> data = new HashMap<>();
		data.put("contactsSequence", contactsSequence);
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
	}
	
	/**
	 * 搜索用户接口
	 * @param reqMsg
	 * @return
	 */
	public String searchUser(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String masterPhone = (String)params.get("masterPhone");
		String searchPhone = (String)params.get("searchPhone");
		if(masterPhone == null || searchPhone == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		if(masterPhone.equals(searchPhone)){
			return GsonUtil.toJson(new ResponseMsg(CODE_LOGIN_USER_SAME, "不能添加自己为好友"));
		}
		
		User user = userService.getUserByPhone(searchPhone);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("phoneNumber", user.getPhoneNumber());
		data.put("name", user.getName() == null ? "" : user.getName());
		
		Contacts contacts = contactsService.getContacts(masterPhone, searchPhone);
		if(contacts == null){
			data.put("isContacts", 0);
		}else{
			data.put("isContacts", 1);
		}
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
		
	}
	
	/**
	 * 获取联系人跟新序号接口
	 * @param reqMsg
	 * @return
	 */
	public String getContactsSequence(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = (String)params.get("phoneNumber");
		if(phoneNumber == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		User user = userService.getUserByPhone(phoneNumber);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
		
		Map<String, String> data = new HashMap<>();
		data.put("contactsSequence", user.getContactsSequence());
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
	}
	
	
	/**
	 * 申请添加好友
	 * @param reqMsg
	 * @return
	 */
	public String applyContacts(String reqMsg){
		ContactsVerify verify = GsonUtil.fromJson(reqMsg, ContactsVerify.class);
		if(verify == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
	    User friend = userService.getUserByPhone(verify.getFriendPhone());
	    if(friend == null){
	    	return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "该用户不存在"));
	    }
		
	    Date time = new Date();
	    
		verify.setType(ContactsVerify.TYPE_APPLY);
		verify.setStatus(ContactsVerify.STATUS_WAIT);
		verify.setApplyTime(time);
		verify.setModifyTime(time);
		contactsService.applyContacts(verify);
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	/**
	 * 分页查询新好友验证记录
	 * @param reqMsg
	 * @return
	 */
	public String pageContactsVerifys(String reqMsg){
	    Map<String, Object> params = GsonUtil.toMap(reqMsg);
	    String masterPhone = null;
		int pageNumber = 0;
		int pageSize = 0;
        try {
        	masterPhone = (String)params.get("masterPhone");
        	pageNumber = new Double((double) params.get("pageNumber")).intValue();
        	pageSize = new Double((double) params.get("pageSize")).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));		
		}
        int start = pageSize * (pageNumber - 1);
	    Pageable<ContactsVerify> pageable = verifyService.pageVerifyByMaster(masterPhone, start, pageSize);
	    
	    ResponseMsg responseMsg = new ResponseMsg(CODE_SUCCESS,"", pageable.getList());
	    responseMsg.setPageNumber(pageNumber);
	    responseMsg.setPageSize(pageSize);
	    responseMsg.setCount(pageable.getCount());
	     
	    return GsonUtil.toJson(responseMsg);
	}
	
	/**
	 * 处理申请验证
	 * @param reqMsg
	 * @return
	 */
	public String handleApplyVerify(String reqMsg){
		 Map<String, Object> params = GsonUtil.toMap(reqMsg);
		 String relationId = null;
		 int status = 0;
		 try {
			 relationId = (String)params.get("relationId");
			 status = new Double((double) params.get("status")).intValue();
		 } catch (Exception e) {
			// TODO: handle exception
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));		
		 }
		 
		 ContactsRelation relation = relationService.getByRelationId(relationId);
		 if(relation == null){
			 return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		 }
		 
		 if(!contactsService.isApplyTimeIn(relation.getApplyTime())){
			 return GsonUtil.toJson(new ResponseMsg(CODE_CONTACT_APPLY_TIMEOUT, "好友申请超时，请重试"));
		 }
		 
		 if(relation.getStatus() == ContactsRelation.STATUS_YES){
			 return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
		 }
		 
		 if(relation.getStatus() != ContactsRelation.STATUS_WAIT){
			 return GsonUtil.toJson(new ResponseMsg(CODE_UNKNOW_ERRO, "操作失败，请重试"));
		 }
		 
		 
		 if(status == 0){
			 contactsService.refuseApply(relationId);
			 return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
			 
		 }
		 
		 contactsService.passApply(relation, false);
		 Contacts contacts = contactsService.getContacts(relation.getToPhone(), relation.getFromPhone());
		
		 return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", buildContactsData(contacts)));
		
		 
	}
	
	private Map<String, Object> buildContactsData(Contacts contacts){
		Map<String, Object> data = new HashMap<>();
		data.put("contactsId", contacts.getContactsId());
		data.put("friendPhone", contacts.getFriendPhone());
		data.put("name", contacts.getName());
		data.put("contactsSequence", userService.getUserByPhone(contacts.getMasterPhone()).getContactsSequence());
		data.put("modifyTime", contacts.getModifyTime());
		
		return data;
	}
	
	/**
	 * 删除新好友验证记录
	 * @param reqMsg
	 * @return
	 */
	public String deleteContactsVerify(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String verifyId = (String)params.get("verifyId");
		if(verifyId == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));	
		}
		
		verifyService.deleteByVerifyId(verifyId);
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	/**
	 * 获取用户信息
	 * @param reqMsg
	 * @return
	 */
	public String getUserInfo(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = (String)params.get("phoneNumber");
		if(phoneNumber == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		User user = userService.getUserByPhone(phoneNumber);
		if(user == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_NO_USER, "用户不存在"));
		}
		
		user.setPublicKey(null);
		
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", user));
	}
	
	/**
	 * 上传用户公钥
	 * @param reqMsg
	 * @return
	 */
	public String submitPublicKey(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = (String)params.get("phoneNumber");
		String publicKey = (String)params.get("publicKey");
		if(phoneNumber == null || publicKey == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		String keySequence = userService.updatePublicKey(phoneNumber, publicKey);
		Map<String, String> data = new HashMap<>();
		data.put("keySequence", keySequence);
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
	}
	
	/**
	 * 获取好友公钥
	 * @param reqMsg
	 * @return
	 */
	public String getFriendPublicKey(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String masterPhone = (String)params.get("masterPhone");
		String friendPhone = (String)params.get("friendPhone");
		String keySequence = (String)params.get("keySequence");
		if(masterPhone == null || friendPhone == null || keySequence == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		
		Contacts contacts = contactsService.getContacts(masterPhone, friendPhone);
	    if(contacts == null){
	    	return GsonUtil.toJson(new ResponseMsg(CODE_NO_CONTACT, "联系人不存在"));
	    }
	    
	    User friendUser = userService.getUserByPhone(friendPhone);
	    
	    if(friendUser.getPublicKey() == null || "".equals(friendUser.getPublicKey())){
	    	return GsonUtil.toJson(new ResponseMsg(CODE_PUBLICKEY_NULL, "公钥不存在"));
	    }
	    
	    if(keySequence.equals(friendUser.getKeySequence())){
	    	return GsonUtil.toJson(new ResponseMsg(CODE_PUBLICKEY_SAME, "公钥版本相同"));
	    }
	    
	    Map<String, String> data = new HashMap<>();
	    data.put("publicKey", friendUser.getPublicKey());
	    
	    return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
	    
	
	}
	
	/**
	 * 检测手机号
	 * @param reqMsg
	 * @return
	 */
	public String checkPhoneNumbers(String reqMsg){
		CheckPhonesParam params = GsonUtil.fromJson(reqMsg, CheckPhonesParam.class);
		String masterPhone = params.getMasterPhone();
		List<String> checkPhones = params.getCheckPhones();
		if(StringUtils.isBlank(masterPhone) || checkPhones.size() == 0){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		Map<String, PhoneCheckResult> map = userService.checkPhoneNumbers(masterPhone, checkPhones);
		Map<String, Object> data = new HashMap<>();
	    data.put("data",  map.values());
	    
	    return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, "", data));
		
	    
	}
	
	/**
	 * 批量申请好友
	 * @param reqMsg
	 * @return
	 */
	public String applyContactsBatch(String reqMsg){
		ApplyBatchParam params = GsonUtil.fromJson(reqMsg, ApplyBatchParam.class);
		if(params == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		String masterPhone = params.getMasterPhone();
		String remark = params.getRemark();
		List<ContactsVerify> applyContacts = params.getApplyContacts();
		if(StringUtils.isBlank(masterPhone) || applyContacts == null){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试")); 
		}
		
		for(ContactsVerify verify : applyContacts){
			Date time = new Date();
			verify.setMasterPhone(masterPhone);
			verify.setRemark(remark);
			verify.setType(ContactsVerify.TYPE_APPLY);
			verify.setStatus(ContactsVerify.STATUS_WAIT);
			verify.setApplyTime(time);
			verify.setModifyTime(time);
			
			contactsService.applyContacts(verify);
		}
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
		
	}
	
	/**
	 * 上传用户个推ClientId
	 * @param reqMsg
	 * @return
	 */
	public String submitClient(String reqMsg){
		Map<String, Object> params = GsonUtil.toMap(reqMsg);
		String phoneNumber = (String)params.get("phoneNumber");
		String clientId = (String)params.get("clientId");
		if(!verifyNullParams(phoneNumber, clientId)){
			return GsonUtil.toJson(new ResponseMsg(CODE_PARAMS_ERR, "操作失败，请重试"));
		}
		
		try{
			userService.updateClient(phoneNumber, clientId);
		}catch(Exception e){
			e.printStackTrace();
			return GsonUtil.toJson(new ResponseMsg(CODE_UNKNOW_ERRO, "操作失败，请重试"));
		}
		
		return GsonUtil.toJson(new ResponseMsg(CODE_SUCCESS, ""));
	}
	
	/**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    
    
    private boolean verifyNullParams(String... params){
        if(params == null || params.length == 0){
            return false;
        }
        for (String param : params){
            if(StringUtils.isBlank(param)){
                return false;
            }
        }
        return true;
    }
	
   
}
