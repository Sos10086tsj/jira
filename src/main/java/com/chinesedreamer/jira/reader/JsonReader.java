package com.chinesedreamer.jira.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * 
 * @author Paris
 * @date Jan 13, 201510:19:27 AM
 * @version beta
 */
public class JsonReader {
	public static JSONObject readJsonFile(String path) {
		String fileStr = readFile(path);
		JSONObject jsonObject = JSONObject.parseObject(fileStr);
		return jsonObject;
	}

	public static String readFile(String path) {
		InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(
				path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		try {
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static Object readJsonObject2Object(JSONObject jsonObject,
			Class<?> classType, String jsonObjectKey)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (StringUtils.isEmpty(jsonObject)) {
			Object obj = classType.newInstance();
			for (Method method : obj.getClass().getDeclaredMethods()) {
				String methodName = method.getName();
				if (method.getName().startsWith("set")) {
					String fieldName = methodName.substring(2).toLowerCase();
					method.invoke(obj, jsonObject.getString(fieldName));
				}
			}
			return obj;
		} else {
			JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectKey);
			return readJsonArray2Object(jsonArray, classType);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object readJsonArray2Object(JSONArray jsonArray, Class<?> classType)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List objs = new ArrayList<>();
		Object obj = classType.newInstance();
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			for (Method method : obj.getClass().getDeclaredMethods()) {
				String methodName = method.getName();
				if (method.getName().startsWith("set")) {
					String fieldName = methodName.substring(3).toLowerCase();
					method.invoke(obj, jsonObject.getString(fieldName));
					objs.add(obj);
				}
			}
		}
		return objs;
	}
}
