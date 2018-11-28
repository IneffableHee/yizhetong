package com.haixia.util;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.service.IRoleService;

public class RoleUtil {
	private static Logger logger = Logger.getLogger(RoleUtil.class);

	@Resource
	private IRoleService roleService;
	public boolean hasPermissiom(Role role,String ckPermission) {
		if(ckPermission == "" || ckPermission ==null)
			return false;
		for (Permission permission : role.getPermissions()) {
				if (ckPermission.equals(permission.getPermissionName())) {
					logger.info("---Permission:"+permission.getPermissionCode());
					return true;
				}
		}
		return false;
	}
}
