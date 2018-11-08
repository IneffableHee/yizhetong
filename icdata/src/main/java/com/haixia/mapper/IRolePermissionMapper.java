package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.RolePermission;

public interface IRolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
    
    Set<RolePermission> getAll();
}