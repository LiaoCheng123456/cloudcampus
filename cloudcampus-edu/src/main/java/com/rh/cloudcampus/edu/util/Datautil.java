package com.rh.cloudcampus.edu.util;

import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.model.PageData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Datautil {

	
	public static void main(String[] args) {

		List<PageData> list2 = new ArrayList<PageData>();
		PageData data2 = new PageData();
		data2.put("s", list2);
		List<PageData> array = JSON.parseArray(data2.get("s").toString(), PageData.class);
		System.out.println(array);
		
		
		
		ArrayList<Object> list = new ArrayList<>();
		PageData data1 = new PageData();
		data1.put("regulatoryIds", list);
		System.out.println(data1.get("regulatoryIds"));
		if(data1.get("regulatoryIds")!=null && !data1.get("regulatoryIds").toString().equals("[]")){
			System.out.println("进入");
		}else {
			System.out.println("没进入");
		}
		
		PageData data = new PageData();
		data.put("areaIds", 0);
		System.out.println(data.get("areaIds").toString().equals("0"));
		System.out.println(data.get("areaIds").toString()=="0");
		System.out.println(data.get("areaIds").toString());
		if(data.get("areaIds")!=null && data.get("areaIds").toString().equals("0")){
			System.out.println("进入了");
		}else {
			System.out.println("没进入");
		}
		
		
		//System.out.println(getAllMonthOfYear("2019"));
	}
	// 获取当前时间戳10位
	public static String getNow() {
		long timeStampSec = System.currentTimeMillis() / 1000;
		String nowTime = String.format("%010d", timeStampSec);
		return nowTime;

	}

	// 获取指定的时间戳10位
	public static int getNowBydate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart;
		int startDay = 0;
		try {
			dateStart = format.parse(date);
			startDay = (int) (dateStart.getTime() / 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDay;
	}

	// 获取当前日期
	public static String getNowTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}
	
	public static String getNowYear() {
		Calendar calendar = Calendar.getInstance();
		String time = getNowTime();
		String string = time.substring(0, 4);
		return string;
	}

	// 获取前一天
	public static String getYesterday() {
		Date date = new Date();// 取时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	// 获取当前月月份
	public static int getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1; 
		return month;
	}
	
	// 获取下个月月份
	public static int getNextMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if(month<12){
			month = month+1;
		}else {
			month = 1;
		}
		return month;
	}
	
	// zc获取传进的年的每个月
		public static List<PageData> getAllMonthOfYear(String time) {
			List<PageData> lst = new ArrayList();
			SimpleDateFormat nowDate = new SimpleDateFormat("yyyy");
			String string = nowDate.format(new Date());
			Calendar c = Calendar.getInstance();
			if (string.equals(time)) {
				SimpleDateFormat nowDate1 = new SimpleDateFormat("yyyy-MM");
				int month = c.get(Calendar.MONTH) + 1;
				for (int i = 1; i <= month; i++) {
					PageData monthData = new PageData();
					StringBuffer b = new StringBuffer(time);
					b.append(i < 10 ? "-0" + i : "-" + i);
					monthData.put("month", b);
					lst.add(monthData);
				}
			} else {
				for (int i = 1; i < 13; i++) {
					PageData monthData = new PageData();
					StringBuffer b = new StringBuffer(time);
					b.append(i < 10 ? "-0" + i : "-" + i);
					monthData.put("month", b);
					lst.add(monthData);
				}
			}
			return lst;
		}
		
		
		
}
