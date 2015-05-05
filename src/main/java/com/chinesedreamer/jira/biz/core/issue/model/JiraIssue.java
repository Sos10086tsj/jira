package com.chinesedreamer.jira.biz.core.issue.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20153:20:20 PM
 * @version beta
 */
@Entity
@Table(name = "t_supp_jira_issue")
public @Getter @Setter class JiraIssue extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -798743551838483899L;

	@Column(name = "jira_id")
	private String jiraId;
	
	@Column
	private String self;
	
	@Column
	private String key;
	
	@Column
	private String description;
	
	@Column
	private Long assigne;
	
	@Column(name = "due_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dueDate;
	
	@Column(name = "issue_type")
	private Long issueType;
	
	@Column
	private Long priority;
	
	@Column
	private Long project;
	
	@Column
	private Long reportor;
	
	@Column
	private Long status;
	
	@Column
	private String summary;
	
	@Column(name = "time_tracking")
	private Long timeTracking;
	
	@Column(name = "time_estemate")
	private Integer timeEstemate;
	
	@Column(name = "time_spent")
	private Integer timeSpent;
	
	@Column(name = "create_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	@Column(name = "update_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
}
