package com.chinesedreamer.jira.biz.core.project.repository;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:11:25 PM
 * @version beta
 */
public interface JiraProjectRepository extends BaseRepository<JiraProject, Long>{
	public JiraProject findByKey(String key);
	public JiraProject findByJiraId(String jiraId);
}
