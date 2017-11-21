package com.xd.service;



import java.io.Serializable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xd.core.token.TokenManager;



/**
 * 
 * @author lenovo
 *
 */
@Service
public class InitCoreConfigListener implements ApplicationListener<ContextRefreshedEvent> {

    

	@Autowired
	UserService userService;
	RedisTemplate<Serializable, Serializable> redisTemplate;
	/**
	 * 更新access_token定时器执行间隔时间 :1小时
	 */	
	public static final int FIRST_TIME = 1 * 1000;	
	
	public static final int TIMER_INTERVAL = 1 * 20 * 60 * 1000;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getApplicationContext().getParent() == null){
			 
//			initCoreConfig();
		
		
	     }
		
		
	}
	
	
	
	
	
	
}
