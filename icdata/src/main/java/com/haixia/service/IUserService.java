package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.User;

public interface IUserService {
	public User getById(int userId);
	public User getByUserName(String userName);
	public User getByUserPhone(String userPhone);
	public Set<String> getUserHomeMenu(User user);
	public Set<String> getAdminHomeMenu(User user);
	public void updateUser(User user);
	public Set<User> getAll();
	public User checkLogin(String sid);
}
