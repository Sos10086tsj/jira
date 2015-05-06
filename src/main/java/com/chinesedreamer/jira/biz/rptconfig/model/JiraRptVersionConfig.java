package com.chinesedreamer.jira.biz.rptconfig.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20153:45:14 PM
 * @version beta
 */
@Entity
@Table(name = "t_biz_daily_report_config")
public @Getter @Setter class JiraRptVersionConfig extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7852230300887844981L;

	@Column(name = "project_jira_id")
	private String projectJiraId;
	
	@Column(name = "version_jira_id")
	private String versionJiraId;
}
