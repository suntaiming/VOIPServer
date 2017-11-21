package com.xd.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csizg.poc.token.TokenManager;
import com.xd.domain.api.ResponseMsg;
import com.xd.domain.api.TokenParam;
import com.xd.util.GsonUtil;

/**
 * 服务间通信相关接口
 * @author lenovo
 *
 */

@Controller
@RequestMapping("")
public class ServerApiController{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 提交token接口
	 */
	@RequestMapping(value = "/serverApi/{cmd}", method = RequestMethod.POST)
	@ResponseBody
	public String serverApi(@PathVariable(value = "cmd") String cmd, String reqMsg){
		
		
		return "";
	
	}
	

	
	
	
	

	
}
