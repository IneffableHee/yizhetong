package com.haixia.util;

import org.apache.log4j.Logger;

import com.haixia.controller.UserController;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;

public class UserUtil {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	private User user;
	
	public UserUtil(User u){
		user = u;
		logger.info("UserUtil");
	}
	
	public boolean hasRole(String ckRole) {
		for (Role role : user.getRoles()) {
			if(ckRole.equals(role.getRoleName().toString())) {
				logger.info("UserUtil hasRole:"+role.getRoleName().toString());
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermissiom(String ckPermission) {
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
}
