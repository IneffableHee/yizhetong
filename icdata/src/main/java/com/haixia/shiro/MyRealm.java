package com.haixia.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.shiro.ShiroUser;

public class MyRealm extends AuthorizingRealm {
	private static Logger logger = Logger.getLogger(MyRealm.class);

	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private IUserService userService;
	String pass;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("Shiro开始登录认证");
		
		String loginName=((UsernamePasswordToken) authcToken).getUsername();
		logger.info("loginName:"+loginName);
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session:sessions){
//			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//			SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
//			User user = (User) session.getAttribute("currentUser");
//			logger.info("session User:"+user.getUserName());
			logger.info("session Name:"+session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)+session.getAttribute("currentUser"));
			if(loginName.equals(session.getAttribute("currentUser"))) {
				session.setTimeout(0);//设置session立即失效,即将其踢出系统
				logger.info("设置session立即失效,即将其踢出系统");
				break;
			}
		}
		 
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getByUserName(token.getUsername());
		// 账号不存在
		if (user == null) {
			return null;
		}
		ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUserName());
		// 认证缓存信息
		logger.info("Shiro登录认证成功");
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());
	}

	/**
	 * Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("Shiro开始授权");
		if (principals == null) {
			return null;
		}

		int userId = (int) principals.fromRealm(getName()).iterator().next();
		User user = userService.getById(userId);
		if (user != null) {
			if (user.getRoles().size() > 0) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for (Role role : user.getRoles()) {
					info.addRole(role.getRoleName());
					for (Permission permission : role.getPermissions()) {
						if (permission.getPermissionCode() != null) {
							info.addStringPermission(permission.getPermissionName());
						}
					}
				}
				return info;
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 清除权限的缓存,但该方法执行后，前端页面的刷新会略有延迟，这是缓存造成的
	 * 
	 * @author jiangCaiJun
	 */
	public void updateAuthz(Long roleId) {
		Set<String> urlSet = new HashSet<String>();
		List<Map<String, String>> roleResourceList = new ArrayList<>();
		if (roleResourceList.size() > 0) {
			for (Map<String, String> map : roleResourceList) {
				if (map != null) {
					urlSet.add(map.get("user"));
				}
			}
		}
		// 注意：此处模拟塞入资源权限对应的标签，实际项目中，应从对应的资源与权限关系表中获取
		urlSet.add("/manage/user");
		urlSet.add("/manage/user/user");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
	}

	// init-method 配置.
	public void setCredentialMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");// MD5算法加密
		credentialsMatcher.setHashIterations(1024);// 1024次循环加密
		setCredentialsMatcher(credentialsMatcher);
	}

}
