package com.haixia.service;

import java.util.Set;

import com.haixia.pojo.Department;

public interface IDepartmentService {
	public Set<Department> getAll();
	public Department getById(int did);
	public Department getByName(String dname);
	public void update(Department department);
	public void create(Department department);
	public void deleteById(int did);
}
