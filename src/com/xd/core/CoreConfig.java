package com.xd.core;

import org.weixin4j.util.MD5;

import com.xd.util.MD5Util;

public class CoreConfig {
	
	/**
	 * 聊天文件上传路径
	 */
	public final static String CHAT_UPLOAD_PATH = "/upload/message/audio/";
	
	/**
	 * 工程根路径（绝对路径）
	 */
	public static String  webRootAbsolutePath = "";
	
	
	/**
	 * 好友申请超时时间
	 */
	public final static long CONTACTS_APPLY_TIMEOUT = 30 * 24 * 60 * 60 * 1000L;
//	public final static long CONTACTS_APPLY_TIMEOUT = 5 * 60 * 1000L;
	
	/**
	 * 客户端服务端约定密码
	 */
	public final static String SERVER_PASSKEY_ClEAR = "xindun.voice.password.server.client";
	
	/**
	 * 客户端服务端约定密文
	 */
	public static String SERVER_PASSKEY;
	
	
/*************************************************短信验证码相关***********************************************************/	

	/**
	 * 创蓝（253）短信验证码 账号
	 */
	public final static String SMS_ACCOUNT = "N6077177";
	/**
	 * 创蓝（253）短信验证码 密码
	 */
	public final static String SMS_PW = "2pNuGnXSjc61a5";
	/**
	 * 手机短信模板
	 */
	public static String smsMessage = "【牛盾密话】您好, 您的验证码是: %d,请在五分钟之内填写。";
	/**
	 * 创蓝（253）短信验证码 是否需要状态报告（默认false），选填
	 */
	public static final String SMS_REPORT = "true";
	/**
	 * 创蓝（253）短信验证码   短信发送url
	 */
	public static final String SMS_URL = "http://smssh1.253.com/msg/send/json";
	/**
	 * 短信验证码有效时间  (毫秒)
	 */
	public static final long SMS_LOSE_TIME = 5 * 60 * 1000;
	
/************************************************************************************************************/	
	
	
	
	
/**********************************推送（个推）相关**************************************************************/	
	/**
	 * 个推appId
	 */
//	public static final String APP_ID = "zyyO4YkhqYANlyecm2K6M";  //平台版
	public static final String APP_ID = "88UIWJsllb8TXq93jCi3J";  //EP2版
	/**
	 * 个推appKey
	 */
//	public static final String APP_KEY = "OzyCNgdDpW5kszut1SaPV4";  //平台版
	public static final String APP_KEY = "TVRDS9vOvC9Tyl6d7jqMr4";  //EP2版
	/**
	 * 个推masterSecret
	 */
//	public static final String MASTER_SECRET = "hZIsHk0LMkAiS2n9hCgzX6"; //平台版
	public static final String MASTER_SECRET = "SfzK38TiJR7bOvSMEt4O23"; //EP2版
	
/***********************************************************************************************************/	
	
	
	static{
		SERVER_PASSKEY = MD5Util.stringMD5(SERVER_PASSKEY_ClEAR);
	}
}
