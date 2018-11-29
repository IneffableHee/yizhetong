package com.haixia.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.RolePermission;
import com.haixia.pojo.User;
import com.haixia.service.IPermissionService;
import com.haixia.service.IRoleService;
import com.haixia.util.RoleUtil;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Resource
	private UserUtil userUtil;
	
	@Resource
	private RoleUtil roleUtil;
	
	@Resource
	private IRoleService roleService;
	
	@Resource
	private IPermissionService permissionService;
	
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
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "role:create")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		if(this.roleService.getByName(jsonRole.getString("roleName"))!=null) {
			json.put("status", 7);
         	json.put("msg","角色已存在");
         	return json.toJSONString();
		}
		
		Role prole = roleService.getByName(jsonRole.getString("parent"));
		String pString = prole.getParentString();
		Role role = new Role();
		role.setCreateUser(currentUser.getId());
		role.setCreateTime(Long.toString(new Date().getTime()));
		role.setDiscription(jsonRole.getString("discription"));
		role.setRoleName(jsonRole.getString("roleName"));
		role.setParentId(prole.getRoleId());
		if(pString == "")
			role.setParentString(prole.getRoleId().toString());
		else
			role.setParentString(pString+'/'+prole.getRoleId().toString());
		role.setRoleStatus(1);
		this.roleService.create(role);
		logger.info("permission:"+jsonRole.getString("permissions"));
		//分配权限
		String[] permissionArray=jsonRole.getString("permissions").split(",");
		if(permissionArray!=null||permissionArray.length!=0){ 
			for(int i=0;i<permissionArray.length;i++){ 
				logger.info("1");
				Permission permission = this.permissionService.getByName(permissionArray[i]);
				logger.info("2");
				if(permission==null)
					continue;
				logger.info("3");
				if(!this.roleUtil.hasPermissiom(prole, permissionArray[i])) {
					continue;
//					json.put("status",8);
//					json.put("msg","未知错误，请联系管理员！");
//					return json.toJSONString();
				}
				logger.info("11");
				RolePermission addRPermission = new RolePermission();
				logger.info("22");
				Role createRole = roleService.getByNameLite(jsonRole.getString("roleName"));
				addRPermission.setRoleId(createRole.getRoleId());
				logger.info("33");
				addRPermission.setPermissionId(permission.getPermissionId());
				logger.info("112233"+permissionArray[i]+","+permission.getPermissionId());
				this.roleService.setPermission(addRPermission);
				logger.info(permissionArray[i]+","+permission.getPermissionId());
			} 
		}
		//到这里了
		
		
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
	
	@ResponseBody
	@RequestMapping("/getChildren")
	public String getChild(@RequestParam("sid") String sid) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		if(!this.userUtil.isAdmin(currentUser)) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		
		Set<String> childrenStr = new TreeSet<String>();
		Set<Role> currentRoles = currentUser.getRoles();
		for (Role role : currentRoles) {  
			Set<Role> childRoles = this.roleService.getChild(role);
			childrenStr.add(role.getRoleName());
			for(Role crole:childRoles) {
				childrenStr.add(crole.getRoleName());
				logger.info(crole.getRoleName());
			}
		}
		
		json.put("status",1);
        json.put("children", JSONObject.toJSON(childrenStr));
		
		return json.toJSONString();
	
	}
	
	@ResponseBody
	@RequestMapping("/getPermissions")
	public String getPermissions(@RequestParam("sid") String sid,@RequestParam("rname") String rname) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		if(!this.userUtil.isAdmin(currentUser)) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限");
         	return json.toJSONString();
         }
		logger.info("rname:"+rname);
		Role role = roleService.getByName(rname);
		logger.info(role.getRoleName());
		
		Set<String> permissionStr = new TreeSet<String>();
		Set<String> menuStr = new TreeSet<String>();
		for(Permission permission : role.getPermissions()) {
			logger.info(permission);
			logger.info(permission.getPermissionName()+","+permission.getPermissionCode()+","+permission.getPermissionType());
			if(permission.getPermissionType().equals("permission")) {
				permissionStr.add(permission.getPermissionName());
			}
			if(permission.getPermissionType().equals("menu")) {
				menuStr.add(permission.getPermissionName());
			}
		}
		
		
		json.put("status",1);
		json.put("permissions", JSONObject.toJSON(permissionStr));
		json.put("menus", JSONObject.toJSON(menuStr));
		return json.toJSONString();
	}

}
