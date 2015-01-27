package com.chinesedreamer.jira.biz.vo;

import java.util.Comparator;

/**
 * Description: 
 * @author Paris
 * @date Jan 27, 20159:25:24 AM
 * @version beta
 */
public class ReportTaskVoComparator implements Comparator<ReportTaskVo>{
	@Override
	public int compare(ReportTaskVo o1, ReportTaskVo o2) {
		if (o1.getKey().startsWith("OTHER-")) {
			Integer number1 = Integer.parseInt(o1.getKey().substring("OTHER-".length()));
			Integer number2 = Integer.parseInt(o2.getKey().substring("OTHER-".length()));
			return number1.compareTo(number2);
		}
		return o1.getKey().compareTo(o2.getKey());
	}
}
