package com.chinesedreamer.jira.biz.core.linktype.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20153:16:10 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_linktype")
public @Getter @Setter class JiraLinkType extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5538461950625073403L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String name;
	
	@Column
	private Boolean inward;
	
	@Column
	private Boolean outward;
}
