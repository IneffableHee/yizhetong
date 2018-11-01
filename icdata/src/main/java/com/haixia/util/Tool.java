package com.haixia.util;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;

import com.haixia.controller.DepartmentController;
import com.haixia.pojo.User;

public class Tool {
	private static Logger logger = Logger.getLogger(Tool.class);
	
	 public static String getUUID() {
		  UUID uuid = UUID.randomUUID();
		  String str = uuid.toString();
		  // 去掉"-"符号
		  String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		  return str+","+temp;
		 }
	 
	 public static boolean checkVerify(User user) {
		 String lastVerify = user.getUserVerify();
		 if(lastVerify == null)
			 return true;
		 String decVerify = Base64.decodeToString(lastVerify);
		 Long lastTime = Long.parseLong(decVerify.substring(6));
		 Long now =System.currentTimeMillis();
		 Long dis = getDistanceTime(now,lastTime);
		 if(dis>=60)
			 return true;
		 logger.info("decVerify:"+decVerify+","+"lastTime:"+lastTime+","+"now:"+now+","+"Distance:"+dis);
		 return false;
	 }
	 
	 public static int pswCheckVerify(User user,String verify) {
		 String lastVerify = user.getUserVerify();
		 if(lastVerify == null)
			 return 0;
		 String decVerify = Base64.decodeToString(lastVerify);
		 if(!decVerify.substring(0, 6).equals(verify)) {
			 return 0;
		 }
		 Long lastTime = Long.parseLong(decVerify.substring(6));
		 Long now =System.currentTimeMillis();
		 Long dis = getDistanceTime(now,lastTime);
		 if(dis>=300)
			 return 1;
		 else 
			 return 2;
	 }
	 
	 public static long getDistanceTime(long time1, long time2) {
		    long day = 0;
		    long hour = 0;
		    long min = 0;
		    long sec = 0;
		    long diff;

		    if (time1 < time2) {
		            diff = time2 - time1;
		        } else {
		            diff = time1 - time2;
		        }
		        day = diff / (24 * 60 * 60 * 1000);
		        hour = (diff / (60 * 60 * 1000) - day * 24);
		        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		        sec = (diff / 1000);
//		        if (day != 0) return day + "天"+hour + "小时"+min + "分钟" + sec + "秒";
//		        if (hour != 0) return hour + "小时"+ min + "分钟" + sec + "秒";
//		        if (min != 0) return min + "分钟" + sec + "秒";
		        return sec;
		}

}
