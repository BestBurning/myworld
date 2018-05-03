package com.diyishuai.redis.list;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date getDate(String dString) throws Exception{
		return simpleDateFormat.parse(dString);
	}
	
	public static String getString(Date date) {
		return simpleDateFormat.format(date);
	}

}
