package com.archer.ssm.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件操作工具类
 *
 */
public class PropertyUtil {

	/**
	 * 获取配置文件值
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public static String getValue(String propertyName, String key) {
		String value = "";
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyName);
			Properties properties = new Properties();
			properties.load(inputStream);
			value = properties.getProperty(key);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	
	public static void main(String[] args) {
		System.out.println(PropertyUtil.getValue("properties/public.properties", "dog_acti_url"));
	}
}
