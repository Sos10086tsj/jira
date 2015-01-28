package com.chinesedreamer.jira.biz.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 不统计story, 新功能
 * @author Paris
 * @date Jan 27, 20152:09:29 PM
 * @version beta
 */
public @Getter @Setter @ToString class ReportTaskAssigneeVo {
	private String username;
	
	private int total = 0;
	private int bugs = 0;
	private double bugRate = 0;//bug 率
	private int onTime = 0;
	private double onTimeRate = 0;//按时交付率
	
	private int totalTimeSpent = 0;
	private String totalTimeSpentStr;
	private int totalEstimated = 0;
	private String totalEstimatedStr;
	private double timeRate = 0;//工作效率
	
	public void merge(ReportTaskAssigneeVo vo){
		this.total += vo.getTotal();
		this.bugs += vo.getBugs();
		this.onTime += vo.getOnTime();
		this.totalTimeSpent += vo.getTotalTimeSpent();
		this.totalEstimated += vo.getTotalEstimated();
	}
}
