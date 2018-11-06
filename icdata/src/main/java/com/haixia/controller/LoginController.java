package com.haixia.controller;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.Tool;
import com.haixia.util.UserUtil;

@Controller
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private IUserService userService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@RequestMapping(value = "/loginTest", method = RequestMethod.POST)
    @ResponseBody
    public String CeShi(@RequestParam("pw") String pw, @RequestParam("name") String name) {
		User user = userService.getById(1);
		JSONObject json= new JSONObject();
        json.put("category", JSONObject.toJSON(user));
        return json.toJSONString();
    }
	
	//普通用户登录认证
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request,@RequestParam("username") String username,  @RequestParam("password") String password,@RequestParam("guid") String guid){
    	System.out.println("username:"+username+"password:"+Base64.decodeToString(password));

    	logger.info("ipaddress"+getIpAddress(request));
    	
        Subject subject = SecurityUtils.getSubject();
        String upassword =  new Sha256Hash(password,"haixia").toString();
        UsernamePasswordToken token = new UsernamePasswordToken(username, upassword);  
        
        JSONObject json= new JSONObject();
        try {
            subject.login(token);
            User user = userService.getByUserName(token.getUsername());  
            if(user == null)
            	user = userService.getByUserPhone(token.getUsername()); 
            
            if(user == null) {
            	json.put("status", 3);
            	json.put("msg","账号不存在");
            	return json.toJSONString();
            }
            
            UserUtil userT = new UserUtil(user);
            logger.info("userT:"+userT);
            if(!userT.hasRole("PartmentStaff")) {
            	json.put("status", 3);
            	json.put("msg","账号不存在");
            	return json.toJSONString();
            }

            Session session = subject.getSession();
            session.setAttribute("currentUser", user.getUserName());                            
            
            //生成key  
            String time=Long.toString(System.currentTimeMillis()); 
//    		String tokenText = time+"-"+user.getId()+"-aspviwn";
    		String textTime = Base64.encodeToString(time.getBytes());
    		String textId = Base64.encodeToString(session.getId().toString().getBytes());
    		String salt = Base64.encodeToString("-aspviwn".getBytes());
    		String tokenText = textId+"&"+textTime+"&"+salt;
    		String bs64Token = Base64.encodeToString(tokenText.getBytes());

            logger.info("loginid:"+session.getId()+"bs64加密解密:"+bs64Token+"[]"+tokenText);
            logger.info("loginipwd:"+Base64.decodeToString(password));
            if(Base64.decodeToString(password)=="123456") {	//默认密码
            	json.put("status", 0);
            	Tool tool = new Tool();
            	logger.info("setUserGuid:"+tool.getUUID());
            	user.setUserGuid(tool.getUUID());
            }else if(guid==null || guid != user.getUserGuid()) {	//新设备或更换设备
            	json.put("status", 2);
            	Tool tool = new Tool();
            	logger.info("setUserGuid:"+tool.getUUID());
            	user.setUserGuid(tool.getUUID());
            }else {		//登陆成功
            	json.put("status", 1);
            	user.setUserState("loginSuccess");
            }
            userService.updateUser(user);
        	json.put("token", bs64Token);
        } catch (UnknownAccountException e) {
            logger.error("账号不存在：{}", e);
            json.put("status", 3);
            json.put("msg","账号不存在");
        } catch (DisabledAccountException e) {
            logger.error("账号未启用：{}", e);
            json.put("status", 3);
            json.put("msg","账号未启用");
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误：{}", e);
            json.put("status", 3);
            json.put("msg","密码错误");
        } catch (ExcessiveAttemptsException e) {
            logger.error("登录失败多次，账户锁定：{}", e);
            json.put("status", 3);
            json.put("msg","登录失败多次，账户锁定");
        } catch (RuntimeException e) {
            logger.error("未知错误,请联系管理员：{}", e);
            json.put("status", 3);
            json.put("msg","未知错误,请联系管理员");
        }
        return json.toJSONString();
    }
    
  //管理员登录认证
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    public String admin(HttpServletRequest request,@RequestParam("username") String username,  @RequestParam("password") String password,@RequestParam("guid") String guid){
    	System.out.println("username:"+username+"password:"+Base64.decodeToString(password)+"guid:"+guid);
    	JSONObject json= new JSONObject();
    	
    	if(username==null||password==null||guid==null) {
    		json.put("status", 3);
        	json.put("msg","用户名或密码错误");
        	return json.toJSONString();
    	}
    	
        Subject subject = SecurityUtils.getSubject();
        String upassword =  new Sha256Hash(password,"haixia").toString();
        UsernamePasswordToken token = new UsernamePasswordToken(username, upassword);  
        
        try {
            subject.login(token);
            User user = userService.getByUserPhone(token.getUsername());
            if(user == null)
            	user = userService.getByUserName(token.getUsername());
            
            if(user == null) {
            	json.put("status", 3);
            	json.put("msg","账号不存在");
            	return json.toJSONString();
            }
            
            logger.info("new UserUtil(user);");
    		UserUtil userT = new UserUtil(user);
    		logger.info("userT.hasRole");
            if(!userT.hasRole("Admin") && !userT.hasRole("GeneralAdmin")) {
            	json.put("status", 3);
            	json.put("msg","账号不存在");
            	return json.toJSONString();
            }
            
            Session session = subject.getSession();
            session.setAttribute("currentUser", user.getUserName());                            
            
            //生成key  
            String time=Long.toString(System.currentTimeMillis()); 
//    		String tokenText = time+"-"+user.getId()+"-aspviwn";
    		String textTime = Base64.encodeToString(time.getBytes());
    		String textId = Base64.encodeToString(session.getId().toString().getBytes());
    		String salt = Base64.encodeToString("-aspviwn".getBytes());
    		String tokenText = textId+"&"+textTime+"&"+salt;
    		String bs64Token = Base64.encodeToString(tokenText.getBytes());

            logger.info("loginid:"+session.getId()+"bs64加密解密:"+bs64Token+"[]"+tokenText);
            if(Base64.decodeToString(password).equals("123456")) {	//默认密码
            	logger.info("默认密码");
            	json.put("status", 0);
            	Tool tool = new Tool();
            	String uuid = tool.getUUID();
            	logger.info("setUserGuid:"+uuid);
            	user.setUserGuid(uuid);
            	json.put("guid", uuid);
            }else if(guid==null || !guid.equals(user.getUserGuid())) {	//新设备或更换设备
            	logger.info("新设备或更换设备,uid:"+user.getUserGuid());
            	json.put("status", 2);
            	json.put("tel",user.getUserPhone());
            	Tool tool = new Tool();
            	String uuid = tool.getUUID();
            	logger.info("setUserGuid:"+uuid);
            	user.setUserGuid(uuid);
            	json.put("guid", uuid);
            }else {		//登陆成功
            	logger.info("登陆成功");
            	json.put("status", 1);
            	user.setUserState("loginSuccess");
            }
            userService.updateUser(user);
        	json.put("token", bs64Token);
        } catch (UnknownAccountException e) {
            logger.error("账号不存在：{}", e);
            json.put("status", 3);
            json.put("msg","账号不存在");
        } catch (DisabledAccountException e) {
            logger.error("账号未启用：{}", e);
            json.put("status", 3);
            json.put("msg","账号未启用");
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误：{}", e);
            json.put("status", 3);
            json.put("msg","密码错误");
        } catch (ExcessiveAttemptsException e) {
            logger.error("登录失败多次，账户锁定：{}", e);
            json.put("status", 3);
            json.put("msg","登录失败多次，账户锁定");
        } catch (RuntimeException e) {
            logger.error("未知错误,请联系管理员：{}", e);
            json.put("status", 3);
            json.put("msg","未知错误,请联系管理员");
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/newDevice", method = RequestMethod.POST)
    @ResponseBody
    public String defaultPwd(HttpServletRequest request,@RequestParam("sid") String sid,  @RequestParam("verify") String verify){
    	System.out.println("sid:"+sid+"verify:"+verify);
    	JSONObject json= new JSONObject();
    	if(sid==null||verify==null) {
    		json.put("status",6);
    		json.put("msg","请重新验证！");
			return json.toJSONString();
    	}
    	
    	UserUtil userT = new UserUtil();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String userName = userT.checkLoginUser(sid,sessions);
		User user =userService.getByUserName(userName);
		if(user == null)
			user =userService.getByUserPhone(userName);

		if(user==null) {
			json.put("status",6);
			json.put("msg","尚未登录，请登录！");
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
    	json.put("status",1);
		return json.toJSONString();
    }
    /**
     * 获取ip地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
        return ip;
    }

}
