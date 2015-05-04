/**
 * 
 */
package com.chinesedreamer.jira.base.model;

import java.io.Serializable;

import javax.persistence.Column;

public class JiraBaseEntity<ID extends Serializable> extends BaseEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4080747680672146557L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;

	public String getJiraId() {
		return jiraId;
	}

	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}
    
	
}
