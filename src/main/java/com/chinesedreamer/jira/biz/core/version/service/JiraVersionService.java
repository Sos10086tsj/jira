package com.chinesedreamer.jira.biz.core.version.service;

import java.util.List;

import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20151:38:11 PM
 * @version beta
 */
public interface JiraVersionService {
	public List<JiraVersion> getProjectVersions(String projectJiraId);
}
