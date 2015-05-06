package com.chinesedreamer.jira.biz.core.version.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.chinesedreamer.jira.base.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20158:55:13 AM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_version")
public @Getter @Setter class JiraVersion extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092898007517598754L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column(name = "release_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseDate;
	
	@Column(name = "project_jira_id")
	private String projectJiraId;
}
