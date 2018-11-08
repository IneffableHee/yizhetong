package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Permission;

public interface IPermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
    
    Set<Permission> getAll();
}