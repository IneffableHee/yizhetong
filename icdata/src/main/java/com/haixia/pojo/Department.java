package com.haixia.pojo;

public class Department {
    private Integer departmentId;

    private String departmentName;

    private String departmentShortName;

    private String departmentDescription;

    private Integer parentDepartmentId;

    private String createTime;

    private String departmentAddress;

    private String departmentUser;

    private String departmentPhone;

    private Integer departmentState;

    private Integer createUser;
    
    public Department(String departmentName,String departmentShortName,String departmentDescription,
    		String departmentAddress,String departmentUser,String departmentPhone) {
    	this.departmentName = departmentName;
    	this.departmentShortName =departmentShortName;
    	this.departmentDescription = departmentDescription;
    	this.departmentAddress = departmentAddress;
    	this.departmentUser = departmentUser;
    	this.departmentPhone = departmentPhone;
    }
   
    public Department() {
    	
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentShortName() {
        return departmentShortName;
    }

    public void setDepartmentShortName(String departmentShortName) {
        this.departmentShortName = departmentShortName == null ? null : departmentShortName.trim();
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription == null ? null : departmentDescription.trim();
    }

    public Integer getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(Integer parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress == null ? null : departmentAddress.trim();
    }

    public String getDepartmentUser() {
        return departmentUser;
    }

    public void setDepartmentUser(String departmentUser) {
        this.departmentUser = departmentUser == null ? null : departmentUser.trim();
    }

    public String getDepartmentPhone() {
        return departmentPhone;
    }

    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone == null ? null : departmentPhone.trim();
    }

    public Integer getDepartmentState() {
        return departmentState;
    }

    public void setDepartmentState(Integer departmentState) {
        this.departmentState = departmentState == null ? null : departmentState;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}