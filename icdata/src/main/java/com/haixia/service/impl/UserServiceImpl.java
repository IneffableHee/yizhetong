package com.haixia.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.haixia.controller.UserController;
import com.haixia.mapper.IUserMapper;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
 
@Service("userService")
public class UserServiceImpl implements IUserService {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserMapper userMapper;
	@Override
	public User getById(int userId) {
		// TODO Auto-generated method stub
		return this.userMapper.getById(userId);
	}
 
	public User getByUserName(String userName) {
		// TODO Auto-generated method stub
		return this.userMapper.getByUserName(userName);
	}
	
	public User getByUserPhone(String userPhone) {
		return this.userMapper.getByUserPhone(userPhone);
	}
	
	public Set<String> getUserHomeMenu(User user){
		Set<String> menuSet = new HashSet<String>();
		for (Role role : user.getRoles()) {
			logger.info("Role:");
			for (Permission permission : role.getPermissions()) {
				if(permission.getParentString().equals("1/8")) {
					menuSet.add(permission.getPermissionName());
					logger.info("---Permission:"+permission.getPermissionUrl());
				}
			}
		}
		return menuSet;
	}
	
	public Set<String> getAdminHomeMenu(User user){
		Set<String> menuSet = new HashSet<String>();
		for (Role role : user.getRoles()) {
			logger.info("Role:");
			for (Permission permission : role.getPermissions()) {
				if(permission.getParentString().equals("1/21")) {
					menuSet.add(permission.getPermissionName());
					logger.info("---Permission:"+permission.getPermissionUrl());
				}
			}
		}
		return menuSet;
	}
	
	public void updateUser(User user) {
		this.userMapper.updateById(user);
	}
	
	public Set<User> getAll(){
		return this.userMapper.getAll();
	}
}
