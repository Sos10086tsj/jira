package com.chinesedreamer.jira.biz.core.version.repository;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20158:57:24 AM
 * @version beta
 */
public interface JiraVersionRepository extends BaseRepository<JiraVersion, Long>{
	public JiraVersion findByJiraId(String jiraId);
}
