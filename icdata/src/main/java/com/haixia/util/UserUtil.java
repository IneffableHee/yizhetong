package com.haixia.util;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;

public class UserUtil {
	private static Logger logger = Logger.getLogger(UserUtil.class);
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Resource
	private IUserService userService;
	
	public boolean hasRole(User user,String ckRole) {
		if(user.getRoles()!=null) {
			logger.info(user.getRoles().size());
			for (Role role : user.getRoles()) {
				logger.info(role.getRoleName().toString());
				if(ckRole.equals(role.getRoleName().toString())) {
					logger.info("UserUtil hasRole:"+role.getRoleName().toString());
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasPermissiom(User user,String ckPermission) {
		for (Role role : user.getRoles()) {
			logger.info("Role:");
			for (Permission permission : role.getPermissions()) {
				if (ckPermission.equals(permission.getPermissionCode())) {
					logger.info("---Permission:"+permission.getPermissionCode());
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasPermissiom(User user,String ckRole,String ckPermission) {
		if(user.getRoles()!=null) {
			logger.info(user.getRoles().size());
			for (Role role : user.getRoles()) {
				logger.info(role.getRoleName().toString());
				if(ckRole.equals(role.getRoleName().toString())) {
					logger.info("UserUtil hasRole:"+role.getRoleName().toString());
					for (Permission permission : role.getPermissions()) {
						if (ckPermission.equals(permission.getPermissionCode())) {
							logger.info("---Permission:"+permission.getPermissionCode());
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public User checkLoginUser(String sid) {
		if(sid == "" || sid == null) 
			return null;
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		logger.info("sid:"+sid);
		String decodeText = Base64.decodeToString(sid);
		logger.info("decodesid:"+decodeText);
		String[] splitstr=decodeText.split("&");
		String uid = Base64.decodeToString(splitstr[0]);
		logger.info("uid:"+uid);
		
		User user=null;
		for(Session session:sessions){
			System.out.println("登录ip:"+session.getHost());
			System.out.println("登录id:"+session.getId());
			System.out.println("登录用户"+session.getAttribute("currentUser"));
			System.out.println("最后操作日期:"+session.getLastAccessTime());
			if(uid.equals(session.getId().toString())) {
				session.setTimeout(1800000);
				String userName = session.getAttribute("currentUser").toString();
				user =userService.getByUserName(userName);
				if(user == null)
					user =userService.getByUserPhone(userName);
				logger.info("currentUser"+userName);
				break;
			}
		}
		return user;
	}
	
	public boolean isAdmin(User user) {
		if(user == null)
			return false;
		if(this.hasRole(user,"Admin") || this.hasRole(user,"GeneralAdmin")) 
			return true;
		return false;
	}
}
