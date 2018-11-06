package com.haixia.controller;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.haixia.pojo.Department;
import com.haixia.pojo.User;
import com.haixia.service.IDepartmentService;
import com.haixia.service.IUserService;
import com.haixia.util.SMSUtil;
import com.haixia.util.UserUtil;


@Controller
@RequestMapping("/department")
public class DepartmentController {
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Resource
	private IDepartmentService departmentService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Resource
	private IUserService userService;
	
	@ResponseBody
	@RequestMapping("/getAll")
	public String getAll(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null || !user.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Set<Department> departments = departmentService.getAll();
		logger.info(departments.size());
		for (Department dpt : departments) {  
			logger.info(dpt.getDepartmentName());
		}  
		
        json.put("department", JSONObject.toJSON(departments));
        return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/get")public String get(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null || !user.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/create")public String create(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null || !user.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/update")public String update(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null || !user.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/delete")public String delete(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null || !user.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		return json.toJSONString();
	}
}
