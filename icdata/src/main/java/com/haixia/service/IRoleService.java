package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.Role;

public interface IRoleService {
	public Set<Role> getAll();
	public Role getById(int rid);
	public Role getByName(String name);
	public void update(Role role);
	public void deleteById(int rid);
	public void create(Role role);
}
