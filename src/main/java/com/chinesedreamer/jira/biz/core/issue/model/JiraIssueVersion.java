package com.chinesedreamer.jira.biz.core.issue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201510:19:46 AM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_issue_version")
public @Getter @Setter class JiraIssueVersion extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9198751276698742414L;

	@Column(name = "issue_jira_id")
	private String issueJiraId;
	
	@Column(name = "version_jira_id")
	private String versionJiraId;
	
	@Column(name = "project_jira_id")
	private String projectJiraId;
}
