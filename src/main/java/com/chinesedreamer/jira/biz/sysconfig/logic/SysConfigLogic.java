package com.chinesedreamer.jira.biz.sysconfig.logic;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20155:14:03 PM
 * @version beta
 */
public interface SysConfigLogic extends BaseLogic<SysConfig, Long>{
	public SysConfig findByProperty(String property);
}
