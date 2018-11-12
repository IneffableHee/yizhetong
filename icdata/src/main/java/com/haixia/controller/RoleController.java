package com.haixia.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IRoleService;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Resource
	private UserUtil userUtil;
	
	@Resource
	private IRoleService roleService;
	
	@ResponseBody
	@RequestMapping("/getAll")
	public String getAll(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Set<Role> roles = this.roleService.getAll();
		logger.info(roles.size());
		for (Role role : roles) {  
			logger.info(role.getRoleName());
		}  
		
        json.put("role", JSONObject.toJSON(roles));
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String get(@RequestParam("sid") String sid,@RequestParam("rid") int rid) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Role role = this.roleService.getById(rid);
		if(role == null){
			json.put("status",5);
			json.put("msg","获取不到用户信息！");
			return json.toJSONString();
		}
		
		json.put("role",role);
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public String create(@RequestParam("sid") String sid,@RequestParam("role") String strRole) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestParam("sid") String sid,@RequestParam("rid") int rid) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "role:delete")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		
		this.roleService.deleteById(rid);
		
		return json.toJSONString();
	}

	@ResponseBody
	@RequestMapping("/update")public String update(@RequestParam("sid") String sid,@RequestParam("role") String strRole) {
		JSONObject json= new JSONObject();
		JSONObject jsonRole = JSONObject.parseObject(strRole); 
		logger.info(sid+"---"+strRole);
		
		User currentUser =this.userUtil.checkLoginUser(sid);
		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "department:update")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		
		Role role = new Role();
//		role.ser(Integer.parseInt(jsonDepartment.getString("departmentId")));
//		role.setDepartmentName(jsonDepartment.getString("departmentName"));
//		role.setDepartmentShortName(jsonDepartment.getString("departmentShortName"));
//		role.setDepartmentDescription(jsonDepartment.getString("departmentDescription"));
		
		this.roleService.updateRole(role);
		
		return json.toJSONString();
	}

}
