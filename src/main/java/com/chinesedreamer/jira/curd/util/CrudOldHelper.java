package com.chinesedreamer.jira.curd.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.curd.vo.CrudOldVo;
import com.chinesedreamer.jira.curd.vo.IssueOldVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 201510:07:23 AM
 * @version beta
 */
public class CrudOldHelper {
	public static String getProject(HttpServletRequest request){
		return request.getParameter("project");
	}
	
	public static String getIssueType(HttpServletRequest request){
		return request.getParameter("issueType");
	}
	
	public static String getVersion(HttpServletRequest request){
		return request.getParameter("version");
	}
	
	public static String getIssueKeys(HttpServletRequest request){
		return request.getParameter("issueKeys");
	}
	
	public static String getTemplateCode(HttpServletRequest request){
		return request.getParameter("templateCode");
	}
	
	public static List<CrudOldVo> getCrudVos(HttpServletRequest request){
		List<CrudOldVo> vos = new ArrayList<CrudOldVo>();
		String requestVos = request.getParameter("crudVos");
		JSONObject rootJsonObject = JSONObject.parseObject(requestVos);
		JSONArray rootJsonArray = rootJsonObject.getJSONArray("crudVos");
		for (Object issueVo : rootJsonArray) {
			JSONObject jo = (JSONObject) issueVo;
			CrudOldVo vo = new CrudOldVo();
			vo.setParentIssueKey(jo.getString("issueKey"));
			
			//develop array
			JSONArray devJsonArray = jo.getJSONArray("developers");
			if (null != devJsonArray && devJsonArray.size() > 0) {
				String[] developers = new String[devJsonArray.size()];
				for (int i = 0; i < devJsonArray.size(); i++) {
					JSONObject devJo = (JSONObject)devJsonArray.get(i);
					developers[i] = devJo.getString("username");
				}
				vo.setDevelopers(developers);
			}
			//qa array
			JSONArray qaJsonArray = jo.getJSONArray("qas");
			if (null != qaJsonArray && qaJsonArray.size() > 0) {
				String[] qas = new String[qaJsonArray.size()];
				for (int i = 0; i < qaJsonArray.size(); i++) {
					JSONObject qaJo = (JSONObject)qaJsonArray.get(i);
					qas[i] = qaJo.getString("username");
				}
				vo.setQas(qas);
			}
			//requirement array
			JSONArray reqJsonArray = jo.getJSONArray("bas");
			if (null != reqJsonArray && reqJsonArray.size() > 0) {
				String[] requirements = new String[reqJsonArray.size()];
				for (int i = 0; i < reqJsonArray.size(); i++) {
					JSONObject reqJo = (JSONObject)reqJsonArray.get(i);
					requirements[i] = reqJo.getString("username");
				}
				vo.setRequirements(requirements);
			}
			//products array
			JSONArray productJsonArray = jo.getJSONArray("products");
			if (null != productJsonArray && productJsonArray.size() > 0) {
				String[] products = new String[productJsonArray.size()];
				for (int i = 0; i < productJsonArray.size(); i++) {
					JSONObject productJo = (JSONObject)productJsonArray.get(i);
					products[i] = productJo.getString("username");
				}
				vo.setProducts(products);
			}
			
			vos.add(vo);
		}
		return vos;
	}
	
	public static List<IssueOldVo> convertIssues2Vo(List<Issue> issues){
		List<IssueOldVo> vos = new ArrayList<IssueOldVo>();
		for (Issue issue : issues) {
			IssueOldVo vo = new IssueOldVo();
			vo.setKey(issue.getKey());
			vo.setSummary(issue.getSummary());
			vo.setAssignee(issue.getAssignee().getDisplayName());
			vo.setFixVersion(issue.getFixVersions().get(0).getName());
			vos.add(vo);
		}
		return vos;
	}
}
