package com.wenzchao.core.util;

import java.util.Map;
import com.alibaba.fastjson.JSON;

/**
 * 数据工具类
 * 
 * @author Venz
 *
 */
public class DataUtil {

	/**
	 * Map转换对象
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
		return JSON.parseObject(JSON.toJSONString(map), clazz);
	}
	
	/**
	 * Map转换对象
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Object strMap2Object(Map<String, String> map, Class<?> clazz) {
		return JSON.parseObject(JSON.toJSONString(map), clazz);
	}
	
}
