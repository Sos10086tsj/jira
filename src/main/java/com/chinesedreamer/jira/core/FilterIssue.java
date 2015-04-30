package com.chinesedreamer.jira.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Description: 
 * @author Paris
 * @date Apr 8, 201511:16:28 AM
 * @version beta
 */
public class FilterIssue extends Resource{

	public FilterIssue(RestClient restclient, JSONObject json) {
		super(restclient);
		if (null != json) {
			this.deserialise(json);
		}
	}
	
	public static List<FilterIssue> get(RestClient restclient, String uri)  throws JiraException{
		List<FilterIssue> filterIssues = new ArrayList<FilterIssue>();
		
		JSONObject jsonObject = realGet(restclient, uri);
		JSONArray jsonArray = jsonObject.getJSONArray("issues");
		for (Object object : jsonArray) {
			filterIssues.add(new FilterIssue(restclient, (JSONObject)object));
		}
		return filterIssues;
	}
	
	private static JSONObject realGet(RestClient restclient, String uri)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(URI.create(uri));
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve filter issue" );
        }
        
        if (!(result instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        return (JSONObject) result;
    }

	private User assignee;
	private User creator;
	private List<Version> fixVersions;
	private List<IssueLink> issueLinks;
	private IssueType issueType;
	private Priority priority;
	private Project project;
	private User reporter;
	private Status status;
	private String summary;
	private String key;
	private Integer timeEstimate;
	private Integer timeSpent;
	private Issue parent;
	private Date resolutionDate;
	
	@SuppressWarnings("rawtypes")
	private void deserialise(JSONObject json){
		Map map = json;
		id = Field.getString(map.get("id"));
		self = Field.getString(map.get("self"));
		key = Field.getString(map.get("key"));
		
		Map fields = (Map)map.get("fields");
		
		assignee = Field.getResource(User.class, fields.get(Field.ASSIGNEE), restclient);
		creator = Field.getResource(User.class, fields.get("creator"), restclient);
		fixVersions = Field.getResourceArray(Version.class, fields.get(Field.FIX_VERSIONS), restclient);
		issueLinks = Field.getResourceArray(IssueLink.class, fields.get(Field.ISSUE_LINKS), restclient);
		issueType = Field.getResource(IssueType.class, fields.get(Field.ISSUE_TYPE), restclient);
		priority = Field.getResource(Priority.class, fields.get(Field.PRIORITY), restclient);
		project = Field.getResource(Project.class, fields.get(Field.PROJECT), restclient);
		reporter = Field.getResource(User.class, fields.get(Field.REPORTER), restclient);
		status = Field.getResource(Status.class, fields.get(Field.STATUS), restclient);
		summary = Field.getString(fields.get(Field.SUMMARY));
		timeEstimate = Field.getInteger(fields.get(Field.TIME_ESTIMATE));
		timeSpent = Field.getInteger(fields.get(Field.TIME_SPENT));
		parent = Field.getResource(Issue.class, fields.get(Field.PARENT), restclient);
		resolutionDate = Field.getDate(fields.get(Field.RESOLUTION_DATE));
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Version> getFixVersions() {
		return fixVersions;
	}

	public void setFixVersions(List<Version> fixVersions) {
		this.fixVersions = fixVersions;
	}

	public List<IssueLink> getIssueLinks() {
		return issueLinks;
	}

	public void setIssueLinks(List<IssueLink> issueLinks) {
		this.issueLinks = issueLinks;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getTimeEstimate() {
		return timeEstimate;
	}

	public void setTimeEstimate(Integer timeEstimate) {
		this.timeEstimate = timeEstimate;
	}

	public Integer getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Integer timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Issue getParent() {
		return parent;
	}

	public void setParent(Issue parent) {
		this.parent = parent;
	}
	

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

}
