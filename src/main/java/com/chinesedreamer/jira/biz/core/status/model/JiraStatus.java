package com.chinesedreamer.jira.biz.core.status.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:45:46 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_status")
public @Getter @Setter class JiraStatus extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4743764328975235631L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String name;
	
	@Column
	private String description;
}
