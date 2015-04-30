package com.chinesedreamer.jira.biz.vo;

import java.util.Comparator;

/**
 * Description: 
 * @author Paris
 * @date Jan 27, 20159:25:24 AM
 * @version beta
 */
public class ReportTaskVoOldComparator implements Comparator<ReportTaskOldVo>{
	@Override
	public int compare(ReportTaskOldVo o1, ReportTaskOldVo o2) {
		// OTHER 前端
		if (o1.getKey().startsWith("OTHER-")) {
			Integer number1 = Integer.parseInt(o1.getKey().substring("OTHER-".length()));
			Integer number2 = Integer.parseInt(o2.getKey().substring("OTHER-".length()));
			return number1.compareTo(number2);
		}
		return o1.getKey().compareTo(o2.getKey());
	}
}
