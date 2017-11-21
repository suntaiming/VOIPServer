package com.xd.test;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.xd.model.User;
import com.xd.service.UserService;
import com.xd.util.GsonUtil;
import com.xd.util.WeixinHttpsUtil;


public class Junit extends AbstractTest {

	@Autowired
	UserService userService;
	@Override
	public void before(){
		System.out.println("--------------------------开始测试--------------------");
		
	}
	
	@Test
	public void byteTest(){
		String url = "http://192.168.1.145:8080/POC/talkServer/api/getOnlineUsers";
		String param = "{\"roomId\":\"1001\"}";
		String json = WeixinHttpsUtil.sendPost(url, param);
		System.out.println(json);
	}
	
	

	
	
	
	@Override
	public void after() {
		// TODO Auto-generated method stub
		System.out.println("--------------------------结束测试---------------------");
	}
}
