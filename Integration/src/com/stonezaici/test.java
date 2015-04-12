package com.stonezaici;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date date = new Date();
		//创建不同的日期格式
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date1 = df1.parse("2013/05/27");
			System.out.println();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println("date1" + date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
