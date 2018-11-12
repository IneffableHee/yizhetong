package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.Role;

public interface IRoleService {
	public Set<Role> getAll();
	public Role getById(int rid);
	public void updateRole(Role role);
	public void deleteById(int rid);
}
