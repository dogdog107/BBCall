package com.bbcall.functions;

import java.util.LinkedHashMap;
import java.util.Map;

public class Tools {

	// ###################
	// 判断是否数字的方法
	// ###################

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// ###################
	// 判断String参数是否为空 / null
	// ###################

	public static boolean isEmpty(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] == null || strings[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public static Map<String, Object> JsonHeadMap(int resultcode, boolean result) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("resultcode", resultcode); // 放入一个是否操作成功的标识
		dataMap.put("errmsg", ResultCode.getErrmsg(resultcode));
		dataMap.put("result", result); // 放入Result
		
		return dataMap;
	}

}
