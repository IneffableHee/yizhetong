package com.haixia.controller;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/index")
public class HomePageController {

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private UserUtil userUtil;
	
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    @ResponseBody
    public String getMenu(HttpServletRequest request,@RequestParam("sid") String sid){
		JSONObject json= new JSONObject();

		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",3);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Set<String> menus = this.userService.getUserHomeMenu(currentUser);
		
		json.put("userName",currentUser.getUserName());
		json.put("currentUserId",currentUser.getId());
		json.put("menu",menus);
		return json.toJSONString();
	}
	

}
