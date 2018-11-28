package com.haixia.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haixia.mapper.IPermissionMapper;
import com.haixia.pojo.Permission;
import com.haixia.service.IPermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService{
	@Resource
	private IPermissionMapper permissionMapper;
	
	public Permission getByName(String pname) {
		return this.permissionMapper.getByName(pname);
	}
	
	public Permission getById(int pid) {
		return this.permissionMapper.getById(pid);
	}
}
