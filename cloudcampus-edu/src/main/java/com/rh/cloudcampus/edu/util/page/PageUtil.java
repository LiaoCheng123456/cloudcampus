package com.rh.cloudcampus.edu.util.page;


import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.model.PageData;


/**
 * @author zc
 * @version 1.0
 */     
public class PageUtil extends BaseData {
	
	public static PageData paging(Integer index, Integer size) {
		PageData data = new PageData();
		int pageindex = (index-1) * size ;
		data.put("pageindex", pageindex);
		data.put("pagesize", size);
		return data;
	}
	
	public static void main(String[] args) {
	PageData paging = paging(3, 2);
	System.out.println("pageindex："+paging.get("pageindex"));
	System.out.println("pagesize:"+paging.get("pagesize"));
	}
}   
