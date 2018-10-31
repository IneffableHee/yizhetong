package com.haixia.pojo;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Integer userId;

    private String userName;

    private String userPass;

    private String userPhone;

    private Integer isAdmin;

    private String registerTime;

    private String lastLoginTime;

    private String lastLoginIp;
    
    private String userGuid;
    
    private String userState;
    
    private String userVerify;

    private Integer departmentId;

    private Set<Role> roleSet = new HashSet<Role>();
    
    public Integer getId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public Set<Role> getRoles() {
		return roleSet;
	}
    
	public void setRoles(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime == null ? null : registerTime.trim();
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime == null ? null : lastLoginTime.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getUserGuid() {
    	return userGuid;
    }
    
    public void setUserGuid(String userGuid) {
    	this.userGuid = userGuid;
    }
    
    public String getUserState() {
    	return userState;
    }
    
    public void setUserState(String userState) {
    	this.userState = userState;
    }
    
    public String getUserVerify() {
    	return userVerify;
    }
    
    public void setUserVerify(String userVerify) {
    	this.userVerify = userVerify;
    }
}