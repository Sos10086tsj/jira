package com.chinesedreamer.jira.biz.core.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20151:33:32 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_user")
public @Getter @Setter class JiraUser extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6329723634875277156L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private Boolean acitve;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column
	private String name;
	
	@Column
	private String email;
}
