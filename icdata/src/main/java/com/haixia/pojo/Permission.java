package com.haixia.pojo;

public class Permission {
    private Integer permissionId;

	private String permissionName;

	private String permissionType;

	private String permissionUrl;

	private String permissionCode;

	private Integer parentId;

	private String parentString;

	private Integer permissionStatus;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName == null ? null : permissionName.trim();
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType == null ? null : permissionType.trim();
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl == null ? null : permissionUrl.trim();
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode == null ? null : permissionCode.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentString() {
		return parentString;
	}

	public void setParentString(String parentString) {
		this.parentString = parentString == null ? null : parentString.trim();
	}

	public Integer getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(Integer permissionStatus) {
		this.permissionStatus = permissionStatus;
	}

}