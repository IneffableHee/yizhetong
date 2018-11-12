package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Department;

public interface IDepartmentMapper {
    int deleteById(Integer departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department getById(Integer departmentId);

    int updateById(Department record);

//    int updateByPrimaryKey(Department record);
    
    Set<Department> getAll();
}