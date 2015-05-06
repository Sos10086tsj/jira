package com.chinesedreamer.jira.email.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IpmEmailConfig {
	public static final String propertyName = "message-config.properties";
	
	public static Properties loadConfig() throws IOException {
		InputStream is = IpmEmailConfig.class.getClassLoader().getResourceAsStream(propertyName);
		Properties prop = new Properties();
		prop.load(is);
		return prop;
	}
}
