package com.haixia.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.Tool;
 
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private IUserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	@ResponseBody
	@RequestMapping("/getUsers")
	public String getUsers() {
		User user = userService.getById(1);
		JSONObject json= new JSONObject();
        json.put("category", JSONObject.toJSON(user));
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
    	user.setUserPass(upassword);
    	userService.updateUser(user);
    	json.put("status", 1);
    	return json.toJSONString();
	}
}
