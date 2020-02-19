package com.rh.cloudcampus.edu.util;
/**
 * 对参数验证非空
 * @author shj-22
 *
 */
public class ParameterUtil {
	/**
	 * 
	 * @return boolean
	 * true 为空
	 * false 非空
	 */
	public static boolean getResult(Object...objects) {
		for(int i=0;objects!=null&&i<objects.length;i++) {
			if(objects[i] == null ||(objects[i].toString().isEmpty())){
				return true;
			}
		}
		return false;
	}
}
