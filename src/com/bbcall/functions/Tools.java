package com.bbcall.functions;

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

}
