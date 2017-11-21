package com.xd.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;


@RequestMapping("/user")
@Controller
public class UserController{


	/**
	 * 用户列表每页条数
	 */
	public static final int pagesize = 8;

	
	@RequestMapping(value="/manager",method=RequestMethod.GET)
	public ModelAndView listUser(String pagenum){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("userManage");
		mv.addObject("sidebar","users");
		int num = 1;
		if(null!=pagenum){
			num = Integer.parseInt(pagenum);
		}
//		List<User> list = userService.listUser((num-1)*pagesize, pagesize,null);
		
		
		return mv;
	}
	
	

	
	
}
