package com.chinesedreamer.jira.biz.core.project.logic;

import java.util.List;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:11:58 PM
 * @version beta
 */
public interface JiraProjectLogic extends BaseLogic<JiraProject, Long>{
	public JiraProject findByKey(String key);
	public JiraProject findByJiraId(String jiraId);
	public List<JiraProject> findAll();
}
