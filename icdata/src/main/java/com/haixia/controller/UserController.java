package com.haixia.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
 
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

}
