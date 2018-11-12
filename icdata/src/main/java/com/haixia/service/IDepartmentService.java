package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.Department;

public interface IDepartmentService {
	public Set<Department> getAll();
	public Department getById(int did);
	public void updateDepartment(Department department);
}
