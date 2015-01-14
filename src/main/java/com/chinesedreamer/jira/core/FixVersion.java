package com.chinesedreamer.jira.core;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 201511:22:38 AM
 * @version beta
 */
public class FixVersion extends Resource{
	
	private String name = null;
    private boolean archived = false;
    private boolean released = false;
    private String releaseDate;
    private String description = null;

    protected FixVersion(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }
    
    public void mergeWith(FixVersion fixVersion) {
        
        JSONObject req = new JSONObject();
        req.put("description", fixVersion.getDescription());
        req.put("name", fixVersion.getName());
        req.put("archived", fixVersion.isArchived());
        req.put("released", fixVersion.isReleased());
        req.put("releaseDate", fixVersion.getReleaseDate());

        try {
            restclient.put(Resource.getBaseUri() + "fixversion/" + id, req);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to merge", ex);
        }
    }
    
    public void copyTo(Project project) {
        
        JSONObject req = new JSONObject();
        req.put("description", getDescription());
        req.put("name", getName());
        req.put("archived", isArchived());
        req.put("released", isReleased());
        req.put("releaseDate", getReleaseDate());
        req.put("project", project.getKey());
        req.put("projectId", project.getId());
      
        try {
            restclient.post(Resource.getBaseUri() + "fixversion/", req);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to copy to project '" + project.getKey() + "'", ex);
        }
    }
    
    public static Version get(RestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "fixversion/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve version " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Version(restclient, (JSONObject) result);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        name = Field.getString(map.get("name"));
        archived = Field.getBoolean(map.get("archived"));
        released = Field.getBoolean(map.get("released"));
        releaseDate = Field.getString(map.get("releaseDate"));
        description = Field.getString(map.get("description"));
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isReleased() {
        return released;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;

    }

}
