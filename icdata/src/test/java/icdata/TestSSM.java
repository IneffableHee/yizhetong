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
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.haixia.pojo.Permission;
import com.haixia.pojo.Role;
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
 
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}
 
	@Test
	public void test1() {
		SMSUtil sms = new SMSUtil();
		SendSmsResponse response;
		try {
			response = sms.sendSms();
			System.out.println("短信接口返回的数据----------------");
		    System.out.println("Code=" + response.getCode());
		    System.out.println("Message=" + response.getMessage());
		    System.out.println("RequestId=" + response.getRequestId());
		    System.out.println("BizId=" + response.getBizId());
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
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
	}
}
