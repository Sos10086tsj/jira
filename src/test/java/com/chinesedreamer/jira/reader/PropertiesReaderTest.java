package com.chinesedreamer.jira.reader;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 20151:05:39 PM
 * @version beta
 */
public class PropertiesReaderTest {

	@Test
	public void testLoadProperties() {
		Properties prop = PropertiesReader.loadProperties();
		assertNotNull(prop);
		System.out.println(prop.getProperty("jira.connection.url"));
	}

}
