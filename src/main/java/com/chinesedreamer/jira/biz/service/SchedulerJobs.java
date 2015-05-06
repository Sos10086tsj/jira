package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201511:06:39 AM
 * @version beta
 */
public interface SchedulerJobs {
	public void sysncJiraIssue() throws JiraException;
}
