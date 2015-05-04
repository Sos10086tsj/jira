package com.chinesedreamer.jira.biz.sysconfig.repository;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20155:11:05 PM
 * @version beta
 */
public interface SysConfigRepository extends BaseRepository<SysConfig, Long>{
	public SysConfig findByProperty(String property);
}
