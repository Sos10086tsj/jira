package com.chinesedreamer.jira.biz.core.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:09:49 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_project")
public @Getter @Setter class JiraProject extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907561642805961961L;
	
	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String key;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
}
