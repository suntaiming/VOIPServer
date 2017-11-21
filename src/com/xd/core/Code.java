package com.xd.core;

/**
 * 响应码
 * @author lenovo
 *
 */
public class Code {

	/**
	 * 失败
	 */
	public static final int CODE_FAIL = -1;    
	/**
	 * 成功
	 */
	public static final int CODE_SUCCESS = 1;  
	/**
	 * token过期
	 */
	public static final int CODE_TOKEN_OUT = 0; 
	/**
	 * 参数错误
	 */
	public static final int CODE_PARAMS_ERR = 100; 
	/**
	 * 用户不存在
	 */
	public static final int CODE_NO_USER = 101;  
	/**
	 * 密码错误
	 */
	public static final int CODE_PW_FAIL = 102;  
	/**
	 * 手机号格式不正确
	 */
	public static final int CODE_PHONE_FAIL = 103;  
	/**
	 * 手机验证码不正确
	 */
	public static final int CODE_VERIFY_CODE_FAIL = 104;  
	/**
	 * 重复注册（改手机号已注册）
	 */
	public static final int CODE_REPEAT_REGISTER = 105;   
		
	/**
	 * 重复添加联系人
	 */
	public static final int CODE_REPEAT_CONTACT = 106;
	/**
	 * 联系人不存在
	 */
	public static final int CODE_NO_CONTACT = 107;
	/**
	 * 联系人申请超时
	 */
	public static final int CODE_CONTACT_APPLY_TIMEOUT = 108;
	/**
	 * 公钥版本相同
	 */
	public static final int CODE_PUBLICKEY_SAME = 109;
	/**
	 * 与登录用户相同
	 */
	public static final int CODE_LOGIN_USER_SAME = 110;
	/**
	 * 公钥不存在
	 */
	public static final int CODE_PUBLICKEY_NULL = 111;
	/**
	 * 指令不存在
	 */
	public static final int CODE_UNKNOW_CMD = 140;
	/**
	 * 未知错误
	 */
	public static final int CODE_UNKNOW_ERRO = 141;
	/**
	 * 验签失败
	 */
	public static final int CODE_VERIFYSIGN_FAIL = 142;
}
