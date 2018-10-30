package com.haixia.controller;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/verification")
public class VerificationCodeController {
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Resource
	private IUserService userService;
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public String get(HttpServletRequest request,@RequestParam("sid") String sid){
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		
		return null;
	}

}
