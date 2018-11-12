package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Role;

public interface IRoleMapper {
    int deleteById(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role getById(Integer roleId);

    int updateById(Role record);

//    int updateByPrimaryKey(Role record);

    Set<Role> getAll();
}