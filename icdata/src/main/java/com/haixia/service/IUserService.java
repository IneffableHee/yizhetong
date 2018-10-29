package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.User;

public interface IUserService {
	public User getById(int userId);
	public User getByUserName(String userName);
	public Set<String> getHomeMenu(User user);
}
