package com.haixia.controller;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;
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

@Controller
@RequestMapping("/home")
public class HomePageController {

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    @ResponseBody
    public String getMenu(HttpServletRequest request,@RequestParam("sid") String sid){
		JSONObject json= new JSONObject();

		User user = checkLoginUser(sid);
		
		if(user==null) {
			json.put("status",3);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Set<String> menus = this.userService.getHomeMenu(user);
		
		json.put("userName",user.getUserName());
		json.put("currentUserId",user.getId());
		json.put("menu",menus);
		return json.toJSONString();
	}
	
	public User checkLoginUser(String sid) {
		logger.info("sid:"+sid);
		String decodeText = Base64.decodeToString(sid);
		logger.info("decodesid:"+decodeText);
		String[] splitstr=decodeText.split("&");
		String uid = Base64.decodeToString(splitstr[0]);
		logger.info("uid:"+uid);
		
		String userName=null;
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session:sessions){
			System.out.println("登录ip:"+session.getHost());
			System.out.println("登录id:"+session.getId());
			System.out.println("登录用户"+session.getAttribute("currentUser"));
			System.out.println("最后操作日期:"+session.getLastAccessTime());
			if(uid.equals(session.getId().toString())) {
				userName = session.getAttribute("currentUser").toString();
				break;
			}
		}
		if(userName==null) {
			return null;
		}
		User user = userService.getByUserName(userName);
		return user;
	}
}
