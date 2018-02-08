package com.lokia.demo.util;

/**
 * @author gushu
 * @date 2018/02/08
 */
public class StringUtils {

	public static boolean isBlank(String str) {
		if(str == null || str.trim().length() == 0){
			return true;
		}
		return false;
	}
}
