package com.chinesedreamer.jira.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Description: 
 * @author Paris
 * @date Apr 8, 201510:10:55 AM
 * @version beta
 */
public class Filter extends Resource{
	
	public Filter(RestClient restclient,JSONObject json) {
		super(restclient);
		if (null != json) {
			this.deserialise(json);
		}
	}
	
	public static List<Filter> getFavourite(RestClient restclient) throws JiraException{
		List<Filter> filters =  new ArrayList<Filter>();
		JSONObject jsonObject = realGetFavourite(restclient);
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for (Object object : jsonArray) {
			filters.add(new Filter(restclient, (JSONObject)object));
		}
		return filters;
	}
	
	private static JSONObject realGetFavourite(RestClient restclient)
            throws JiraException {

        JSON result = null;

        try {
            URI uri = restclient.buildURI(getBaseUri() + "filter/favourite" );
            result = restclient.get(uri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve filter ");
        }
        
        if (!(result instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        return (JSONObject) result;
    }

	private String description;
	private boolean favourite;
	private String jpl;
	private String name;
	private String searchUrl;
	private String viewUrl;
	
	
	private void deserialise(JSONObject json) {
		@SuppressWarnings("rawtypes")
		Map map = json;

		//super
		id = Field.getString(map.get("id"));
		self = Field.getString(map.get("self"));
		
		description = Field.getString(map.get("description"));
		favourite = Field.getBoolean(map.get("favourite"));
		jpl = Field.getString(map.get("jql"));
		name = Field.getString(map.get("name"));
		searchUrl = Field.getString(map.get("searchUrl"));
		viewUrl = Field.getString(map.get("viewUrl"));
    }


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isFavourite() {
		return favourite;
	}


	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}


	public String getJpl() {
		return jpl;
	}


	public void setJpl(String jpl) {
		this.jpl = jpl;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSearchUrl() {
		return searchUrl;
	}


	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}


	public String getViewUrl() {
		return viewUrl;
	}


	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	@Override
	public String toString() {
		return "Filter [description=" + description + ", favourite="
				+ favourite + ", jpl=" + jpl + ", name=" + name
				+ ", searchUrl=" + searchUrl + ", viewUrl=" + viewUrl + "]";
	}
	
	
}
