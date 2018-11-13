package com.haixia.mapper;

import java.util.Set;

import com.haixia.pojo.Department;

public interface IDepartmentMapper {
    int deleteById(Integer departmentId);

	int create(Department record);

	Department getById(Integer departmentId);
	
	Department getByName(String departmentName);

    int updateById(Department department);

    Set<Department> getAll();
}