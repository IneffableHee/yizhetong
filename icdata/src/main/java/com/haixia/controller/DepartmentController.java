package com.haixia.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.Department;
import com.haixia.pojo.User;
import com.haixia.service.IDepartmentService;
import com.haixia.service.IUserService;
import com.haixia.util.UserUtil;


@Controller
@RequestMapping("/department")
public class DepartmentController {
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Resource
	private IDepartmentService departmentService;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private UserUtil userUtil;
	
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
		
		Set<Department> departments = departmentService.getAll();
		logger.info(departments.size());
		for (Department dpt : departments) {  
			logger.info(dpt.getDepartmentName());
		}  
		
        json.put("department", JSONObject.toJSON(departments));
        return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String get(@RequestParam("sid") String sid,@RequestParam("did") int did) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);
		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",4);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		
		Department department = departmentService.getById(did);
		if(department == null){
			json.put("status",5);
			json.put("msg","获取不到机构信息！");
			return json.toJSONString();
		}
		
		json.put("department",department);
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public String create(@RequestParam("sid") String sid,@RequestParam("department") String strDepartment) {
		JSONObject json= new JSONObject();
		JSONObject jsonDepartment = JSONObject.parseObject(strDepartment); 
		logger.info(sid+"---"+strDepartment);
		User currentUser =this.userUtil.checkLoginUser(sid);
		long d = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前时间：" + sdf.format(d)+","+d);
		
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
		logger.info(jsonDepartment.getString("departmentName")+jsonDepartment.getString("departmentShortName"));
		if(this.departmentService.getByName(jsonDepartment.getString("departmentName"))!=null || 
				this.departmentService.getByName(jsonDepartment.getString("departmentShortName"))!=null) {
			logger.info("assasa");
			json.put("status", 7);
         	json.put("msg","机构已存在");
         	return json.toJSONString();
		}
		
		Department department = new Department();
		department.setCreateTime(Long.toString(new Date().getTime()));
		department.setCreateUser(currentUser.getId());
		department.setDepartmentAddress(jsonDepartment.getString("departmentAddress"));
		department.setDepartmentDescription(jsonDepartment.getString("departmentDescription"));
		department.setDepartmentName(jsonDepartment.getString("departmentName"));
		department.setDepartmentPhone(jsonDepartment.getString("departmentPhone"));
		department.setDepartmentShortName(jsonDepartment.getString("departmentShortName"));
		department.setDepartmentState(1);
		department.setDepartmentUser(jsonDepartment.getString("departmentUser"));
		String parent = jsonDepartment.getString("parent");
		if(parent != null ) {
			Department parentD = this.departmentService.getByName(parent);
			if(parentD!=null)
				department.setParentDepartmentId(parentD.getDepartmentId());	
		}
		this.departmentService.create(department);
		json.put("status", 1);
     	json.put("msg","创建成功");
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String update(@RequestParam("sid") String sid,@RequestParam("department") String strDepartment) {
		JSONObject json= new JSONObject();
		JSONObject jsonDepartment = JSONObject.parseObject(strDepartment); 
		logger.info(sid+"---"+strDepartment);
		
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
		
		Department department = new Department();
		department.setDepartmentId(Integer.parseInt(jsonDepartment.getString("departmentId")));
		department.setDepartmentName(jsonDepartment.getString("departmentName"));
		department.setDepartmentShortName(jsonDepartment.getString("departmentShortName"));
		department.setDepartmentDescription(jsonDepartment.getString("departmentDescription"));
		
		this.departmentService.update(department);
		
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestParam("sid") String sid,@RequestParam("did") int did) {
		JSONObject json= new JSONObject();
		
		User currentUser =this.userUtil.checkLoginUser(sid);

		if(currentUser==null || !currentUser.getUserState().equals("loginSuccess")) {
			json.put("status",3);
			json.put("msg","尚未登录，请登录！");
			return json.toJSONString();
		}
		if(!this.userUtil.isAdmin(currentUser)||!this.userUtil.hasPermissiom(currentUser, "department:delete")) {
         	json.put("status", 6);
         	json.put("msg","没有操作权限！");
         	return json.toJSONString();
         }
		if(this.departmentService.getById(did)==null) {
			json.put("status", 7);
         	json.put("msg","机构不存在！");
         	return json.toJSONString();
		}
		this.departmentService.deleteById(did);
		
		json.put("status",1);
		json.put("msg","删除成功！");
		return json.toJSONString();
	}
}
