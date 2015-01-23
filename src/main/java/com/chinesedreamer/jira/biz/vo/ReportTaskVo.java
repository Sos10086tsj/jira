package com.chinesedreamer.jira.biz.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 201511:15:13 AM
 * @version beta
 */
public @Getter @Setter @ToString class ReportTaskVo {
	private String key;
	private String summary;
	private Date dueDate;
	private String status;
	private String assignee;
	private String issueType;
	private int timeSpent;
	private String timeSpentStr;
	private int timeEstimated;
	private String timeEstimatedStr;
	private Date resolutionDate;
	private boolean deliveryOnTime = false;
	private List<ReportTaskVo> subTasks;
	private List<ReportTaskVo> includedTasks;
	
	public List<ReportTaskVo> getSubTasks(){
		if (null == this.subTasks) {
			subTasks = new ArrayList<ReportTaskVo>();
		}
		return this.subTasks;
	}
	
	public List<ReportTaskVo> getIncludedTasks(){
		if (null == this.includedTasks) {
			includedTasks = new ArrayList<ReportTaskVo>();
		}
		return this.includedTasks;
	}
}
