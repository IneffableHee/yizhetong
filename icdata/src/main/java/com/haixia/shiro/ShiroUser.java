package com.haixia.shiro;

public class ShiroUser {
	private static final long serialVersionUID = -1373760761780840081L;
    public Integer userId;
    public String userName;
    public String userSession;

    public ShiroUser( Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
