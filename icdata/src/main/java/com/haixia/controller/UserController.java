package com.haixia.controller;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.Department;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.Tool;
import com.haixia.util.UserUtil;
 
@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(DepartmentController.class);

	@Resource
	private IUserService userService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
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
		
		Set<User> users = userService.getAll();
		logger.info(users.size());
		for (User iuser : users) {  
			logger.info(iuser.getUserName());
		}  
		
        json.put("department", JSONObject.toJSON(users));
        return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/get")public String get(@RequestParam("sid") String sid) {
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
	@RequestMapping("/create")public String create(@RequestParam("sid") String sid) {
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
	@RequestMapping("/update")public String update(@RequestParam("sid") String sid) {
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
	@RequestMapping("/delete")public String delete(@RequestParam("sid") String sid) {
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
	@RequestMapping("/changePassword")
	public String changePassword(@RequestParam("tel") String tel,  @RequestParam("password") String password,@RequestParam("verify") String verify) {
		System.out.println("tel:"+tel+"password:"+Base64.decodeToString(password)+"verify:"+verify);
    	JSONObject json= new JSONObject();
    	
    	User user = userService.getByUserPhone(tel);
    	if(user == null) {
    		json.put("status", 5);
    		json.put("msg", "账户不存在！");
    		return json.toJSONString();
    	}
    	Tool tool = new Tool();
    	if(tool.pswCheckVerify(user,verify)==0) {
    		json.put("status", 6);
    		json.put("msg", "验证码错误！");
    		return json.toJSONString();
    	}
    	if(tool.pswCheckVerify(user,verify)==1) {
    		json.put("status", 6);
    		json.put("msg", "验证码超时，请重新获取！");
    		return json.toJSONString();
    	}
        String upassword =  new Sha256Hash(password,"haixia").toString();
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(Base64.decodeToString(password));
        if(!matcher.matches()) {
        	json.put("status", 7);
        	json.put("msg", "密码格式错误！");
    		return json.toJSONString();
        }
        String guid = tool.getUUID();
        user.setUserGuid(guid);
    	user.setUserPass(upassword);
    	userService.updateUser(user);
    	json.put("guid",guid);
    	json.put("status", 1);
    	return json.toJSONString();
	}
}
