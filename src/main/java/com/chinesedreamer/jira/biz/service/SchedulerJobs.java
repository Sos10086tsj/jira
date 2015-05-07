package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201511:06:39 AM
 * @version beta
 */
public interface SchedulerJobs {
	/**
	 * 每天凌晨同步最新issue
	 * @throws JiraException
	 */
	public void sysncJiraIssue() throws JiraException;
	
	/**
	 * 每天项目报告
	 * @throws JiraException
	 */
	public void dailyReport() throws JiraException;
	
	/**
	 * 周报，包括用户信息，项目信息
	 * @throws JiraException
	 */
	public void weeklyReport() throws JiraException;
	
	public void monthlyReport() throws JiraException ;
}
