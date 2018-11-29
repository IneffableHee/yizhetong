package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.Role;
import com.haixia.pojo.RolePermission;

public interface IRoleService {
	public Set<Role> getAll();
	public Set<Role> getChild(Role role);
	public Role getById(int rid);
	public Role getByName(String name);
	public Role getByNameLite(String name);
	public void update(Role role);
	public void deleteById(int rid);
	public void create(Role role);
	public void setPermission(RolePermission addRPermission);
}
