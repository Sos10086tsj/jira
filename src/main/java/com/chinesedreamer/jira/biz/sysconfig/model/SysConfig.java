package com.chinesedreamer.jira.biz.sysconfig.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.chinesedreamer.jira.base.model.BaseEntity;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20155:09:44 PM
 * @version beta
 */
@Entity
@Table(name = "t_sys_config")
public @Getter @Setter class SysConfig extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050518646734402821L;

	@Column
	private String property;
	
	@Column(name = "property_value")
	private String propertyValue;
}
