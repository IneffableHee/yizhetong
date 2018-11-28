package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Permission;

public interface IPermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);

    Permission getById(Integer permissionId);
	
    Permission getByName(String permissionName);
	
    Set<Permission> getAll();
}