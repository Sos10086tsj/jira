package com.chinesedreamer.jira.biz.rptconfig.logic;

import java.util.List;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:11:04 PM
 * @version beta
 */
public interface JiraRptVersionConfigLogic extends BaseLogic<JiraRptVersionConfig, Long>{
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId);
	public List<JiraRptVersionConfig> findAll();
}
