package com.haixia.controller;

import java.util.Date;
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
		logger.info("role/getAll  begin");
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "role:qury")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		
		Set<Role> roles = this.roleService.getAll();
		logger.info(roles.size());
		for (Role role : roles) {  
			logger.info(role.getRoleName());
		}  
		
        json.put("roles", JSONObject.toJSON(roles));
		
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
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "role:get")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
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
		
		JSONObject jsonRole = JSONObject.parseObject(strRole); 
		User currentUser =this.userUtil.checkLoginUser(sid);
		logger.info(strRole);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "department:create")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		if(this.roleService.getByName(jsonRole.getString("roleName"))!=null) {
			json.put("status", 7);
         	json.put("msg","角色已存在");
         	return json.toJSONString();
		}
		
		Role role = new Role();
		role.setCreateUser(currentUser.getId());
		role.setCreateTime(Long.toString(new Date().getTime()));
		role.setDiscription(jsonRole.getString("discription"));
		role.setRoleName(jsonRole.getString("roleName"));
		role.setRoleStatus(1);
		
		this.roleService.create(role);
		json.put("status", 1);
     	json.put("msg","创建成功");
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
		
		if(this.roleService.getById(rid)==null) {
			json.put("status", 7);
         	json.put("msg","机构不存在！");
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
		if(jsonRole.getString("roleDiscription")!=null)
			role.setDiscription(jsonRole.getString("roleDiscription"));
		if(jsonRole.getString("roleName")!=null)
			role.setRoleName(jsonRole.getString("roleName"));
		if(jsonRole.getString("roleStatus")!= null)
			role.setRoleStatus(Integer.parseInt(jsonRole.getString("roleStatus")));
		
		this.roleService.update(role);
		
		return json.toJSONString();
	}

}
