package com.haixia.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
		this.roleMapper.getAll();
		return null;
	}

	@Override
	public Role getById(int rid) {
		// TODO Auto-generated method stub
		this.roleMapper.getById(rid);
		return null;
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		this.roleMapper.updateById(role);
	}
	
	@Override
	public void deleteById(int rid) {
		this.roleMapper.deleteById(rid);
	}

}
