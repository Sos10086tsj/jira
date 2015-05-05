package com.chinesedreamer.jira.biz.core.priority.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:37:57 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_priority")
public @Getter @Setter class JiraPriority extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877320572861027214L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String name;
}
