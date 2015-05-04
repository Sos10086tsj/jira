package com.chinesedreamer.jira.biz.core.priority.model;

import javax.persistence.Column;
import javax.persistence.Table;


import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.JiraBaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:37:57 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_priority")
public @Getter @Setter class JiraPriority extends JiraBaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877320572861027214L;

	@Column
	private String name;
}
