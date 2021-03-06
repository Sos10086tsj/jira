package com.chinesedreamer.jira.biz.core.version.logic;

import java.util.Date;
import java.util.List;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20158:58:00 AM
 * @version beta
 */
public interface JiraVersionLogic extends BaseLogic<JiraVersion, Long>{
	public JiraVersion findByJiraId(String jiraId);
	
	public List<JiraVersion> findByProjectJiraId(String projectJiraId);
	
	public List<JiraVersion> findByReleaseDateBetween(Date start, Date end);
}
