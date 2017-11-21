package com.xd.core.token;

import java.math.BigInteger;
import java.nio.Buffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

//import com.xd.model.UserToken;

public class Token {
	
	/**
	 * 旧token临时生效最大时长2分钟
	 */
	public static final long INTERIM_TIME = 1 * 60 * 1000;
	
	//主键
	private int id;
	//token id
	private String tokenId;
	//用户id
	private String ipocid;
	//最新token
	private String newToken;
	//旧token
	private String oldToken;
	//终端id
	private String terminalId;
	//最新token创建时间 单位毫秒
	private long newCreateTime;
	//旧token创建时间   单位毫秒
	private long oldCreateTime;
	
	
	
	private Token() {
		super();
	}

	
	/**
	 * 创建新的token实例，自动生成token字符串
	 * @param ipocid
	 * @param newToken
	 * @return
	 */
	public static Token newInstance(String ipocid){
		if(StringUtils.isBlank(ipocid)){
			throw new NullPointerException("参数为空");
		}
		return newInstance(ipocid, makeNewTokenStr(ipocid));
		
	}
	
	/**
	 * 创建新的token实例，自动生成token字符串
	 * @param ipocid
	 * @param newToken
	 * @return
	 */
	/*public static Token newInstance(UserToken userToken){
		if(userToken == null){
			throw new NullPointerException("参数为空");
		}
		Token token = new Token();
		token.ipocid = userToken.getIpocid();
		token.newCreateTime = userToken.getNewCreateTime();
		token.newToken = userToken.getNewToken();
		token.oldCreateTime = userToken.getOldCreateTime();
		token.oldToken = userToken.getOldToken();
		token.terminalId = userToken.getTerminalId();
		token.tokenId = userToken.getTokenId();
		return token;
	}
	*/
	
	/**
	 * 创建新的token实例
	 * @param ipocid
	 * @param tokenStr
	 * @return
	 */
	public static Token newInstance(String ipocid, String tokenStr){
		if(StringUtils.isBlank(ipocid) || StringUtils.isBlank(tokenStr)){
			throw new NullPointerException("参数为空");
		}
		Token instance = new Token();
		instance.tokenId = ipocid + System.currentTimeMillis();
		instance.ipocid = ipocid;
		instance.newToken = tokenStr;
		instance.newCreateTime = System.currentTimeMillis();
		
		return instance;
	}
	
	/**
	 * 生成新的token字符串
	 * 参数加时间戳拼成的字符串 经过MD5加密得到的字符串即为token
	 * 
	 * @param param
	 * @return
	 */
	public final static String makeNewTokenStr(String param){
		if(param == null){
			throw new NullPointerException();
		}
		String str = param + System.currentTimeMillis();
		StringBuilder token = new StringBuilder();
		token.append(md5t(str))
		    .append("-")
		    .append(param);
		return token.toString();
	}
	
	private final static String md5t(String text) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
            		text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
     }
	
	/**
	 * 获取用户id
	 * @return
	 */
	public String getIpocid(){
		return this.ipocid;
	}
	/**
	 * 获取token唯一标识
	 * @return
	 */
	public String getTokenId(){
		return this.tokenId;
	}
	
	/**
	 * 校验token是否有效
	 * 规则：首先校验新的token是否通过。
	 *     如果不通过再校验旧的token是否通过（临时生效时间内）。
	 * @param token
	 * @param past
	 * @return
	 */
    public boolean verify(String token, long past){
    	long now = System.currentTimeMillis();
    	if((now - past) >= this.newCreateTime){
    		return false;
    	}
    	if(this.newToken.equals(token)){
    		return true;
    	}
    	if(StringUtils.isBlank(this.oldToken)){
    		return false;
    	}
    	if((now - INTERIM_TIME) >= this.newCreateTime){
    		return false;
    	}
    	if(this.oldToken.equals(token)){
    		return true;
    	}
    	
    	return false;
    	
    }
    
    /**
     * 获取token字符串
     * @return
     */
    public String getTokenStr(){
    	return this.newToken;
    }
	
    /**
     * 更新token， token字符串程序自动生成
     * @param newToken
     */
	public String updateToken() {
		this.oldToken = this.newToken;
		this.oldCreateTime = this.newCreateTime;
		this.newToken = makeNewTokenStr(this.ipocid);
		this.newCreateTime = System.currentTimeMillis();
		
		return this.newToken;
	}
	/**
	 * 更新token
	 * @param newToken
	 */
	public String updateToken(String tokenStr) {
		if(StringUtils.isBlank(tokenStr)){
			throw new NullPointerException("token字符串为空");
		}
		this.oldToken = this.newToken;
		this.oldCreateTime = this.newCreateTime;
		this.newToken = tokenStr;
		this.newCreateTime = System.currentTimeMillis();
		
		return this.newToken;
	}
	
	
	
	

}
