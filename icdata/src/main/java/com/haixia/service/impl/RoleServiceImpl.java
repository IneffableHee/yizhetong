package com.haixia.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.haixia.controller.DepartmentController;
import com.haixia.mapper.IRoleMapper;
import com.haixia.pojo.Role;
import com.haixia.service.IRoleService;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	
	@Resource
	private IRoleMapper roleMapper;

	@Override
	public Set<Role> getAll() {
		// TODO Auto-generated method stub
		return this.roleMapper.getAll();
	}

	@Override
	public Role getById(int rid) {
		// TODO Auto-generated method stub
		return this.roleMapper.getById(rid);
	}
	
	@Override
	public Role getByName(String name) {
		// TODO Auto-generated method stub
		return this.roleMapper.getByName(name);
	}

	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub
		this.roleMapper.updateById(role);
	}
	
	@Override
	public void create(Role role) {
		// TODO Auto-generated method stub
		this.roleMapper.create(role);
	}
	
	@Override
	public void deleteById(int rid) {
		this.roleMapper.deleteById(rid);
	}

}
