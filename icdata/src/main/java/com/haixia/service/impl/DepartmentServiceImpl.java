package com.haixia.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haixia.mapper.IDepartmentMapper;
import com.haixia.pojo.Department;
import com.haixia.service.IDepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {
	@Resource
	private IDepartmentMapper departmentrMapper;
	
	public Set<Department> getAll(){
		return this.departmentrMapper.getAll();
	}
}
