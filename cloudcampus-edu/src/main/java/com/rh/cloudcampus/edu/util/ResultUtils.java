package com.rh.cloudcampus.edu.util;

import com.rh.cloudcampus.edu.util.Const;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;

/**
 * 错误返回工具类
 * @author rex
 *
 */
public class ResultUtils {

	/**
	 * 获取统一返回400错误类（默认）
	 * @param
	 * @return WSPResult
	 */
	public static WSPResult getFAILResult(){
			return getFAILResult(WSPCode.FAIL, Const.FAIL_STR);
	}
	
	/**
	 * 获取返回400错误类--自定义
	 * @param  code  msg msgEng
	 * @return WSPResult 
	 */
	public static WSPResult getFAILResult(String code ,String msg){
		WSPResult  sr = new WSPResult();
		sr.setCode(code);
		sr.setMsg(msg);
		return sr;
	}	
	
}