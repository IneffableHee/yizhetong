package com.haixia.controller;

import java.util.Collection;
import java.util.List;
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
@RequestMapping("/home")
public class AdminHomeController {
private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    @ResponseBody
    public String getMenu(HttpServletRequest request,@RequestParam("sid") String sid){
		JSONObject json= new JSONObject();
		if(sid == "" || sid == null) {
			json.put("status",3);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}

		loginUtil userT = new loginUtil();
		logger.info("checkLoginUser2 begin,sid:"+sid);
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		User currentUser = userT.checkLoginUser(request,sid,sessions);
//		UserUtil userT = new UserUtil();
//		logger.info("checkLoginUser2 begin");
//		User currentUser = userT.checkLoginUser2(sid);
//		Collection<Session> sessions = sessionDAO.getActiveSessions();
//		String userName = userT.checkLoginUser(sid,sessions);
//		User user =userService.getByUserName(userName);
//		if(user == null)
//			user =userService.getByUserPhone(userName);

		if(currentUser==null) {
			json.put("status",3);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
//		Set<User> users = this.userService.getAll();
//		logger.info(users);
//		
//		Set<String> menus = this.userService.getAdminHomeMenu(currentUser);
		
		json.put("userName",currentUser.getUserName());
		json.put("currentUserId",currentUser.getId());
//		json.put("menu",menus);
		return json.toJSONString();
	}
}
