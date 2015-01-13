package com.chinesedreamer.jira.reader;

import java.util.Properties;


/**
 * Description: 
 * @author Paris
 * @date Jan 13, 20151:03:51 PM
 * @version beta
 */
public class PropertiesReader {
	public static Properties loadProperties(){
		Properties prop = new Properties();
		try {
			prop.load(PropertiesReader.class.getClassLoader().getResourceAsStream("config.properties"));
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			return prop;
		}
	}
}
