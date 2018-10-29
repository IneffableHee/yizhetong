package icdata;

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
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
import com.haixia.pojo.User;
import com.haixia.service.IUserService;
 
@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
 
public class TestSSM {
	private static Logger logger = Logger.getLogger(TestSSM.class);
//	private ApplicationContext ac = null;
	@Resource
	private IUserService userService = null;
 
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}
 
	@Test
	public void test1() {
		User user = userService.getById(1);
//		User user = userService.getByUserName("刘德华");
		// System.out.println(user.getUserName());
		for (Role role : user.getRoles()) {
			logger.info(role.getRoleName());
			for(Permission permission:role.getPermissions()) {
				logger.info(permission.getPermissionName());
			}
		}
		logger.info("getByUsername 值："+user.getUserName()+user.getPassword()+user.getUserName());
		logger.info(JSON.toJSONString(user));
	}
}
