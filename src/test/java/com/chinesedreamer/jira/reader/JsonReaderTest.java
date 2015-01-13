package com.chinesedreamer.jira.reader;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:25:40 AM
 * @version beta
 */
public class JsonReaderTest {

	@Test
	public void testReadJsonFile() {
		String path = "datasource/jira-users.json";
		JSONObject object = JsonReader.readJsonFile(path);
		assertNotNull(object);
		System.out.println(object);
	}

}
