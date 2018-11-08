package icdata;

import java.util.Set;

import javax.annotation.Resource;
 
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.haixia.mapper.IPermissionMapper;
import com.haixia.mapper.IRolePermissionMapper;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.RolePermission;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
import com.haixia.util.SMSUtil;
 
@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
 
public class TestSSM {
	private static Logger logger = Logger.getLogger(TestSSM.class);
//	private ApplicationContext ac = null;
	@Resource
	private IUserService userService = null;
	
	@Resource
	private IPermissionMapper permission;
	
	@Resource
	private IRolePermissionMapper rolePermission;
 
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}
 
//	@Test
//	public void test1() {
////		SMSUtil sms = new SMSUtil();
////		SendSmsResponse response;
////		try {
////			response = sms.sendSms();
////			System.out.println("短信接口返回的数据----------------");
////		    System.out.println("Code=" + response.getCode());
////		    System.out.println("Message=" + response.getMessage());
////		    System.out.println("RequestId=" + response.getRequestId());
////		    System.out.println("BizId=" + response.getBizId());
////		} catch (ClientException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	    
//		
//		User user = userService.getById(1);
////		User user = userService.getByUserName("刘德华");
//		// System.out.println(user.getUserName());
//		for (Role role : user.getRoles()) {
//			logger.info(role.getRoleName());
//			for(Permission permission:role.getPermissions()) {
//				logger.info(permission.getPermissionName());
//			}
//		}
//		logger.info("getByUsername 值："+user.getUserName()+user.getPassword()+user.getUserName());
//		logger.info(JSON.toJSONString(user));
//	}
	
	@Test
	public void setAdminRolePermisson() {
		Set<RolePermission> rolePermissions = this.rolePermission.getAll();
		Set<Permission> permissions = this.permission.getAll();
		logger.info("permission length:"+permissions.size());
		for(Permission permission:permissions) {
//			logger.info("permission id:"+permission.getPermissionId()+" permission name:"+permission.getPermissionName());
			int state = 0;
			for(RolePermission rolePermission:rolePermissions) {
//				logger.info("role:"+rolePermission.getRoleId()+"permission:"+rolePermission.getPermissionId());
				if(rolePermission.getRoleId()==1 && rolePermission.getPermissionId()==permission.getPermissionId()) 
					state = 1 ;
			}
			if(state == 0) {
				logger.info("admin添加权限："+permission.getPermissionName());
				RolePermission addRPermission = new RolePermission();
				addRPermission.setRoleId(1);
				addRPermission.setPermissionId(permission.getPermissionId());
				this.rolePermission.insertSelective(addRPermission);
			}
		}
		for(RolePermission rolePermission:rolePermissions) {
			logger.info("role:"+rolePermission.getRoleId()+"permission:"+rolePermission.getPermissionId());
		}
	}
}
