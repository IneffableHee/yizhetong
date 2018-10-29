package com.haixia.mapper;

import com.haixia.pojo.Permission;

public interface IPermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
}