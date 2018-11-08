package com.haixia.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private SessionDAO sessionDAO;
	
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
			logger.info("Role:"+role);
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
			logger.info("Role:"+role);
			for (Permission permission : role.getPermissions()) {
				if(permission.getParentString().equals("1/21")) {
					menuSet.add(permission.getPermissionCode());
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
	
	public User checkLogin(String sid) {
		logger.info("checkLoginUser sid:"+sid);
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		String decodeText = Base64.decodeToString(sid);
		logger.info("decodesid:"+decodeText);
		String[] splitstr=decodeText.split("&");
		String uid = Base64.decodeToString(splitstr[0]);
		logger.info("uid:"+uid);
		

		String userName = null;
		for(Session session:sessions){
			System.out.println("登录ip:"+session.getHost());
			System.out.println("登录id:"+session.getId());
			System.out.println("登录用户"+session.getAttribute("currentUser"));
			System.out.println("最后操作日期:"+session.getLastAccessTime());
			if(uid.equals(session.getId().toString())) {
				logger.info("currentUser:"+session.getId().toString());
				session.setTimeout(1800000);
				logger.info("currentUser setTimeout ");
				userName = session.getAttribute("currentUser").toString();
				break;
			}
		}
		if(userName==""||userName==null)
			return null;
		logger.info("currentUser getAttribute userName:"+userName);
		User user =this.getByUserName(userName);
		logger.info("currentUser getByUserName:"+user);
		if(user == null)
			user =this.getByUserPhone(userName);
		logger.info("currentUser"+userName);
		return user;
	}
}
