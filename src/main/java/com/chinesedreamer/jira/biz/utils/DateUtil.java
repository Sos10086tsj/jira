package com.chinesedreamer.jira.biz.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Description: 
 * @author Paris
 * @date May 7, 201511:02:33 AM
 * @version beta
 */
public class DateUtil {
	public static Date calculateDate(Date date, int inteval){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, inteval);
		return calendar.getTime();
	}
	
	public static String formatTimeTracking(int time){
		StringBuffer buffer = new StringBuffer();
		int minutes = time / 60;
		int hours = minutes / 60;
		int days = hours / 24;
		if (days > 0) {
			buffer.append(days + "天");
		}
		if (hours > 0 && hours % 24 != 0) {
			buffer.append( (hours - 24 * days) + "小时");
		}
		if (minutes > 0 && minutes % 60 != 0) {
			buffer.append( (minutes - hours * 60) + "分");
		}
		if(buffer.length() == 0) {
			buffer.append("0");
		}
		return buffer.toString();
	}
}
