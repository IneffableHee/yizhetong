package com.haixia.pojo;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private Integer roleId;

    private String roleName;

    private String createTime;

    private String discription;

    private Integer createUser;
    
    private Integer roleStatus;
    
    private Integer parentId;
    
    private String parentString;

    private Set<Permission> permissionSet = new HashSet<>();
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
    
    public Set<Permission> getPermissions() {
		return permissionSet;
	}
    
	public void setPermissions(Set<Permission> permissionSet) {
		this.permissionSet = permissionSet;
	}

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription == null ? null : discription.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
    
    public String getParentString() {
        return parentString;
    }

    public void setParentString(String parentString) {
        this.parentString = parentString;
    }
    
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}