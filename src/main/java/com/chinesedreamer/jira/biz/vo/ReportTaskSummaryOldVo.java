package com.chinesedreamer.jira.biz.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 27, 20152:12:57 PM
 * @version beta
 */
public @Getter @Setter @ToString class ReportTaskSummaryOldVo {
	public int totalTasks = 0;
	public int completedTasks = 0;
	public double completedRate = 0;
	public int onTime = 0;
	public double onTimeRate = 0;
	public int workingMinutes = 0;
	public String  workingMinutesStr;
	public int totalMinutes = 0;
	public String totalMinutesStr;
	public double workingRate = 0;//耗时比
	public Map<String, Integer> constitution;//任务组成：任务、子任务、bug
	public double bugRate = 0;
	
	public Map<String, Integer> getConstitution(){
		if (null == constitution) {
			constitution = new HashMap<String, Integer>();
		}
		return constitution;
	}
	
	public void addTotalTasks(){
		this.totalTasks += 1;
	}
	
	public void addCompletedTasks(){
		this.completedTasks += 1;
	}
	
	public void addOnTime(){
		this.onTime += 1;
	}
	
	public void addWorkingMinutes(int minutes){
		this.workingMinutes += minutes;
	}
	
	public void addTotalMinutes(int minutes){
		this.totalMinutes += minutes;
	}
}
