package com.xd.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import com.gexin.rp.sdk.template.style.Style0;
import com.xd.core.CoreConfig;

/**
 * 推送（个推）api
 * @author lenovo
 *
 */
@Service
public class PushMsgService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 个推请求
	 */
	private IGtPush push = null;
	

	/**
	 * 初始化个推请求
	 */
	public PushMsgService() {
	    push = new IGtPush(CoreConfig.APP_KEY, CoreConfig.MASTER_SECRET, true);
	}
	
	
    public static void main(String[] args) {
		PushMsgService push = new PushMsgService();
		push.pushMsg("304bb0df175cde0c06e9aa86c8b94b94", "nihao");
	}
	
	/**
	 * 推送透传消息
	 * @param clientId
	 * @param content
	 */
	public boolean pushMsg(String clientId, String content){
		if(StringUtils.isBlank(clientId)){
			return false;
		}
		TransmissionTemplate template = buildTransmissionTemplate(content);
		IPushResult result = pushMessageToSingle(clientId, template);
		Map<String, Object> reMap = result.getResponse();
		logger.info("推送人:{},推送内容:{}",clientId, content);
		if(reMap.get("result").equals("ok")){
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 推送通知（点击通知打开应用）
	 * @param clientId   CID
	 * @param title      通知标题
	 * @param alert      通知内容
	 * @param content    透传内容
	 */
	public boolean pushAlert(String clientId, String title, String alert, String content){
		if(StringUtils.isBlank(clientId)){
			return false;
		}
		Style0 style0 = buildStyle0(title, alert);
		NotificationTemplate template = buildNotificationTemplate(content, style0);
		IPushResult result = pushMessageToSingle(clientId, template);
		Map<String, Object> reMap = result.getResponse();
		if(reMap.get("result").equals("ok")){
			return true;
		}
		
		return false;
	}
	
	
	
	

	
	/**
	 * 对单个用户推送消息
	 * @param clientId
	 * @param template
	 * @return
	 */
	private IPushResult pushMessageToSingle(String clientId, ITemplate template){
		SingleMessage message = buildSingleMessage(template);
		Target target = buildTarget(clientId);
		
		IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            logger.info("推送失败");
//            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            logger.info("个推结果:{}", ret.getResponse().toString());
        } else {
        	logger.info("个推服务器响应异常");
        }
        return ret;
	}
	
	
	/**
	 * 构建推送目标
	 * @param alias  别名
	 * @return
	 */
	private Target buildTarget(String clientId){
		Target target = new Target();
        target.setAppId(CoreConfig.APP_ID);
        target.setClientId(clientId);
//        target.setAlias(alias);
        return target;
	}
	
	/**
	 * 构建单推消息的消息体
	 * @param template
	 * @return
	 */
	private SingleMessage buildSingleMessage(ITemplate template){
		SingleMessage message = new SingleMessage();
        message.setOffline(false);
        message.setOfflineExpireTime(24 * 3600 * 1000);  //离线有效时间，单位为毫秒，可选
        message.setData(template);
        message.setPushNetWorkType(0);   //可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        
        return message;
	}
	
	/**
	 * 构建通知栏消息布局样式(Style0 系统样式 Style1 个推样式 Style4 背景图样式 Style6 展开式通知样式)
	 * @param title  通知标题
	 * @param alert  通知内容
	 * @return
	 */
	private Style0 buildStyle0(String title, String alert){
		Style0 style = new Style0();
		style.setRing(true);         //是否响铃
		style.setVibrate(true);     //是否震动
		style.setClearable(true);    //是否可清除通知
		style.setTitle(title);      
		style.setText(alert);
		style.setLogo("logo.png");   //通知栏图标
        style.setLogoUrl("");        //通知栏网络图标
        
        return style;
	}
	
	/**
	 * 构建点击通知打开应用模板
	 * @param content    透传内容
	 * @param style      通知栏消息布局样式
	 * @return
	 */
	private NotificationTemplate buildNotificationTemplate(String content, AbstractNotifyStyle style){
		NotificationTemplate template = new NotificationTemplate();
        template.setAppId(CoreConfig.APP_ID);
        template.setAppkey(CoreConfig.APP_KEY);
        template.setTransmissionType(2);          //透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionContent(content);  //透传的内容
        template.setStyle(style);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        
        return template; 
	}
	
	/**
	 * 构建透传消息模板
	 * @param message
	 * @return
	 */
	private TransmissionTemplate buildTransmissionTemplate(String message){
		TransmissionTemplate template = new TransmissionTemplate();
		logger.info("appId:{}", CoreConfig.APP_ID);
		logger.info("appKey:{}", CoreConfig.APP_KEY);
	    template.setAppId(CoreConfig.APP_ID);
	    template.setAppkey(CoreConfig.APP_KEY);
	    //透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent(message);
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    
	    return template;
	}
}
