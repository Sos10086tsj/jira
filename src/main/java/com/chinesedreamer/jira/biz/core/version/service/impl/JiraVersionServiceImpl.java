package com.chinesedreamer.jira.biz.core.version.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.version.logic.JiraVersionLogic;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;
import com.chinesedreamer.jira.biz.core.version.service.JiraVersionService;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20151:38:56 PM
 * @version beta
 */
@Service
public class JiraVersionServiceImpl implements JiraVersionService{
	@Resource
	private JiraVersionLogic logic;

	@Override
	public List<JiraVersion> getProjectVersions(String projectJiraId) {
		return this.logic.findByProjectJiraId(projectJiraId);
	}

}
