package com.chinesedreamer.jira.biz.core.version.repository;

import java.util.Date;
import java.util.List;

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
	public List<JiraVersion> findByProjectJiraId(String projectJiraId);
	public List<JiraVersion> findByReleaseDateBetween(Date start, Date end);
}
