package com.haixia.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.Department;
import com.haixia.pojo.User;
import com.haixia.service.IDepartmentService;
import com.haixia.service.IUserService;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/home")
public class AdminHomeController {
private static Logger logger = Logger.getLogger(AdminHomeController.class);
	
	@Resource
	private IUserService userService;
	
	@Resource
	private UserUtil userUtil;
	
	@Resource
	private IDepartmentService departmentService;
	
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    @ResponseBody
    public String getMenu(HttpServletRequest request,@RequestParam("sid") String sid){
		JSONObject json= new JSONObject();

		User currentUser =this.userUtil.checkLoginUser(sid);
		
		if(!this.userUtil.isAdmin(currentUser)) {
         	json.put("status", 3);
         	json.put("msg","账号不存在");
         	return json.toJSONString();
         }
		
		Set<String> menus = this.userService.getAdminHomeMenu(currentUser);
		
		json.put("userName",currentUser.getUserName());
		json.put("currentUserId",currentUser.getId());
		json.put("menu",menus);
		return json.toJSONString();
	}
	
	@RequestMapping(value = "/getStatistics", method = RequestMethod.POST)
    @ResponseBody
    public String getStatistics(HttpServletRequest request,@RequestParam("sid") String sid){
		JSONObject json= new JSONObject();

		User currentUser =this.userUtil.checkLoginUser(sid);
		if(!this.userUtil.isAdmin(currentUser)) {
         	json.put("status", 3);
         	json.put("msg","账号不存在");
         	return json.toJSONString();
         }
		
		Set<Department> departments = departmentService.getAll();
		
		json.put("departments",departments);
		json.put("butiezhonglei",60);
		json.put("butiehushu",600);
		json.put("butiejine",60000);
		return json.toJSONString();
	}
}
