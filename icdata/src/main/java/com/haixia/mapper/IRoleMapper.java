package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Role;

public interface IRoleMapper {
    int deleteById(Integer roleId);

    int create(Role record);

    Role getById(Integer roleId);
    
    Role getByName(String roleName);
    
    Role getByNameLite(String roleName);

    int updateById(Role record);

    Set<Role> getAll();
    
    Set<Role> getChildren(String parentString);
}