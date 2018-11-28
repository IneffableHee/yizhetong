package com.haixia.service;

import com.haixia.pojo.Permission;

public interface IPermissionService {
	public Permission getByName(String pname);
	public Permission getById(int pid);
}
