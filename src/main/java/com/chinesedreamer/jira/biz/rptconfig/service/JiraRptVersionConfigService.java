package com.chinesedreamer.jira.biz.rptconfig.service;

import java.util.List;

import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:13:57 PM
 * @version beta
 */
public interface JiraRptVersionConfigService {
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId);
	public List<JiraRptVersionConfig> getAll();
}
