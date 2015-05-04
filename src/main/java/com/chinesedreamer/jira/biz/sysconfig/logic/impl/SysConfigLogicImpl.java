package com.chinesedreamer.jira.biz.sysconfig.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.sysconfig.logic.SysConfigLogic;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;
import com.chinesedreamer.jira.biz.sysconfig.repository.SysConfigRepository;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20155:14:35 PM
 * @version beta
 */
@Service
public class SysConfigLogicImpl extends BaseLogicImpl<SysConfig, Long> implements SysConfigLogic{
	@Resource
	private SysConfigRepository repository;
	@Override
	public SysConfig findByProperty(String property) {
		return this.repository.findByProperty(property);
	}

}
