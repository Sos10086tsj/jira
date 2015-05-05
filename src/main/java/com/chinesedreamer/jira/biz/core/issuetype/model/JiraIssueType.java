package com.chinesedreamer.jira.biz.core.issuetype.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:24:36 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_issue_type")
public @Getter @Setter class JiraIssueType extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7658350435671215075L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String name;
	
	@Column
	private String description;
}
