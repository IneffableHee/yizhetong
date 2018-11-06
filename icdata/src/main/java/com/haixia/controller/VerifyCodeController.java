package com.haixia.controller;

import java.util.Collection;

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
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.SMSUtil;
import com.haixia.util.Tool;
import com.haixia.util.UserUtil;

@Controller
@RequestMapping("/verify")
public class VerifyCodeController {
	private static Logger logger = Logger.getLogger(VerifyCodeController.class);
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Resource
	private IUserService userService;
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public String get(HttpServletRequest request,@RequestParam("tel") String tel,@RequestParam("type") String password){
		JSONObject json= new JSONObject();
		User user =userService.getByUserPhone(tel);
			
		if(user==null) {
			json.put("status",4);
			json.put("msg","尚未注册，或手机号错误！");
			return json.toJSONString();
		}
		Tool tool = new Tool();
		if(!tool.checkVerify(user)) {
			json.put("status",4);
			json.put("msg","获取验证码过于频繁，请稍后再试！");
			return json.toJSONString();
		}
		logger.info("send msg begin");
		SMSUtil sms = new SMSUtil();
		String verifyCode = sms.getVerifyCode();
        SendSmsResponse response;
		try {
			response = sms.sendSms(tel,verifyCode,password);
			logger.info(response.getCode());
			if(response.getCode().toString().toString().equals("OK")) {
				logger.info("sendSms Sunccess!");
			}else if(response.getCode().toString().equals("isv.BUSINESS_LIMIT_CONTROL")){
				json.put("status",4);
				json.put("msg","短信验证次数超过限制,请24小时后重试！");
				return json.toJSONString();
			}else{
				json.put("status",4);
				json.put("msg","发送失败，请重试或联系管理员！");
				return json.toJSONString();
			}
			
		} catch (ClientException e) {
			e.printStackTrace();
			logger.info(e);
			json.put("status",4);
			json.put("msg","发送失败，请重试或联系管理员！");
			return json.toJSONString();
		}
		String time=Long.toString(System.currentTimeMillis()); 
		String verify = Base64.encodeToString((verifyCode+time).getBytes());
		logger.info("verify:"+verify);
		user.setUserVerify(verify);
		user.setUserState("changePwd");
		userService.updateUser(user);
		json.put("status",1);
		return json.toJSONString();
	}

}
