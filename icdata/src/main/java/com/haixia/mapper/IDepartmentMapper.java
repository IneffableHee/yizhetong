package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Department;

public interface IDepartmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    Set<Department> getAll();
}